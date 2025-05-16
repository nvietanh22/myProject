package vn.lottefinance.pdms_core.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vn.lottefinance.pdms_core.domain.CampainNotiDetail;
import vn.lottefinance.pdms_core.domain.CustomerConsent;
import vn.lottefinance.pdms_core.domain.CustomerConsentLog;
import vn.lottefinance.pdms_core.domain.SystemConfig;
import vn.lottefinance.pdms_core.enums.CampainNotiStatusEnum;
import vn.lottefinance.pdms_core.enums.ConsentSourceEnum;
import vn.lottefinance.pdms_core.enums.NotiChannelEnum;
import vn.lottefinance.pdms_core.enums.RecordStatusEnum;
import vn.lottefinance.pdms_core.repository.CampainNotiDetailRepository;
import vn.lottefinance.pdms_core.repository.CustomerConsentLogRepository;
import vn.lottefinance.pdms_core.repository.CustomerConsentRepository;
import vn.lottefinance.pdms_core.repository.SystemConfigRepository;
import vn.lottefinance.pdms_core.service.core.NotificationCustomerProcessService;
import vn.lottefinance.pdms_core.service.external.EsbService;
import vn.lottefinance.pdms_core.service.external.RedisQueueService;
import vn.lottefinance.pdms_core.service.external.dto.esb.EsbDTO;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class CampainNotiTask {

    private final CampainNotiDetailRepository campainNotiDetailRepository;
    private final RedisQueueService redisQueueService;
    private final SystemConfigRepository systemConfigRepository;
    private final EsbService esbService;
    private final CustomerConsentRepository customerConsentRepository;
    private final NotificationCustomerProcessService notificationCustomerProcessService;
    private final CustomerConsentLogRepository customerConsentLogRepository;

    @Value("${spring.data.redis.queue.campain-noti}")
    private String campainNotiQueue;

    @Scheduled(cron = "${app.schedule.campain-noti.cron}", zone = "${app.schedule.campain-noti.time-zone}")
    public void scheduleReversalTask() {
        log.info("Process push cash code expired at {}", LocalDateTime.now());
        List<CampainNotiDetail> campainNotiDetailList = campainNotiDetailRepository.findCampainNotiDetailsByStatus(CampainNotiStatusEnum.PENDING.getCode()).orElse(null);

        if (campainNotiDetailList != null && !campainNotiDetailList.isEmpty()) {
            for (CampainNotiDetail item : campainNotiDetailList) {
                redisQueueService.pushToQueue(String.valueOf(item.getId()), campainNotiQueue);
            }
            log.info("Enqueued {} campaign notification:", campainNotiDetailList.size());
        } else {
            log.info("No data campaign notification push to queue");
        }
    }

    @Scheduled(fixedDelay = 180000)
    @Transactional
    public void processQueueCampaignNotification() {
        log.info("fetch queue campaign notification at {}", Instant.now());
        int processedCount = 0;
        int maxProcess = 100;
        String id = redisQueueService.popFromQueue(campainNotiQueue);

        while (StringUtils.isNotBlank(id) && processedCount < maxProcess) {
            processedCount++;
            try {
                CampainNotiDetail detail = campainNotiDetailRepository.findById(Long.parseLong(id)).orElse(null);
                if (detail == null || !CampainNotiStatusEnum.PENDING.getCode().equals(detail.getStatus())) {
                    log.warn("Skip campaign notification {}: not found or not is status pending", id);
                    continue;
                }

                boolean sent;
                String notiChannel = "";
                if (StringUtils.isNotBlank(detail.getNotiChannel())) {
                    sent = notificationCustomerProcessService.sendNotification(detail, NotiChannelEnum.valueOf(detail.getNotiChannel().toUpperCase()));
                    notiChannel = detail.getNotiChannel();
                } else {
                    Map<String, Object> resultSend = notificationCustomerProcessService.sendNotificationWithPriority(detail);
                    sent = (boolean) resultSend.get("result");
                    notiChannel = (String)resultSend.get("channel");
                }

                detail.setStatus(sent ? CampainNotiStatusEnum.SUCCESS.getCode() : CampainNotiStatusEnum.ERROR.getCode());
                campainNotiDetailRepository.save(detail);
                CustomerConsent customerConsent = customerConsentRepository.findFirstByIdCardNumber(detail.getIdCardNumber()).orElse(null);
                // them vao bang customer consent t/h chua tung consent
                if (customerConsent == null) {
                    customerConsent = CustomerConsent.builder()
                            .cifNumber(detail.getCifNumber())
                            .customerName(detail.getCustomerName())
                            .createBy("admin")
                            .createdDate(LocalDateTime.now())
                            .phoneNumber(detail.getPhoneNumber())
                            .email(detail.getEmail())
                            .idCardNumber(detail.getIdCardNumber())
                            .refId(String.valueOf(detail.getId()))
                            .build();
                    customerConsent = customerConsentRepository.save(customerConsent);

                }

                CustomerConsentLog customerConsentLog = CustomerConsentLog.builder()
                        .customerConsent(customerConsent)
                        .status(detail.getStatus())
                        .sourceChannel(ConsentSourceEnum.PDMS.getVal())
                        .notiContent("Todo")
                        .notiChannel(notiChannel)
                        .customerName(detail.getCustomerName())
                        .createdDate(LocalDateTime.now())
                        .createBy("admin")
                        .build();

                customerConsentLogRepository.save(customerConsentLog);
            } catch (Exception ex) {
                log.error("Error process campaign notification id {}: {}", id, ex.getMessage(), ex);
            } finally {
                redisQueueService.removeFromQueue(id, campainNotiQueue);
                id = redisQueueService.popFromQueue(campainNotiQueue);
            }
        }
    }
}
