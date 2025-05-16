package vn.lottefinance.pdms_core.service.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import vn.lottefinance.pdms_core.domain.CampainNotiDetail;
import vn.lottefinance.pdms_core.domain.SystemConfig;
import vn.lottefinance.pdms_core.enums.CampainNotiStatusEnum;
import vn.lottefinance.pdms_core.enums.NotiChannelEnum;
import vn.lottefinance.pdms_core.enums.RecordStatusEnum;
import vn.lottefinance.pdms_core.repository.SystemConfigRepository;
import vn.lottefinance.pdms_core.service.external.EsbService;
import vn.lottefinance.pdms_core.service.external.dto.esb.EsbDTO;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationCustomerProcessService {
    private final SystemConfigRepository systemConfigRepository;
    private final EsbService esbService;


    public boolean sendNotification(CampainNotiDetail detail, NotiChannelEnum channel) {
        try {
            switch (channel) {
                case SMS:
                    SystemConfig smsConfig = systemConfigRepository.findFirstByCodeAndStatus("SMS_CAMPAIN_CONTENT"
                            , RecordStatusEnum.ACTIVE.getVal()).orElse(null);
                    if (smsConfig == null) return false;

                    EsbDTO.SmsRequest smsRequest = EsbDTO.SmsRequest.builder()
                            .channel("PDMS")
                            .message(smsConfig.getStrVal())
                            .phone(detail.getPhoneNumber())
                            .build();
                    return CampainNotiStatusEnum.SUCCESS.getCode().equals(esbService.sendSms(smsRequest).getErrorCode());

                case EMAIL:
                    SystemConfig emailConfig = systemConfigRepository.findFirstByCodeAndStatus("EMAIL_CAMPAIN_ID"
                            , RecordStatusEnum.ACTIVE.getVal()).orElse(null);
                    if (emailConfig == null) return false;

                    Map<String, String> data = Map.of("billingDate", "10/2021");
                    EsbDTO.EmailRequest emailRequest = EsbDTO.EmailRequest.builder()
                            .mailTo(detail.getEmail())
                            .templateId(emailConfig.getIntVal())
                            .title("Thông báo")
                            .data(data)
                            .build();
                    return CampainNotiStatusEnum.SUCCESS.getCode().equals(esbService.sendEmail(emailRequest).getErrorCode());

                case ZNS:
                    SystemConfig znsConfig = systemConfigRepository.findFirstByCodeAndStatus("ZNS_CAMPAIN_CONTENT"
                            , RecordStatusEnum.ACTIVE.getVal()).orElse(null);
                    if (znsConfig == null) return false;

                    Map<String, String> param = Map.of("test", "test data");
                    EsbDTO.ZnsRequest znsRequest = EsbDTO.ZnsRequest.builder()
                            .code("PDMS_CONSENT")
                            .channel("PDMS_CONSENT")
                            .param(param)
                            .build();
                    return CampainNotiStatusEnum.SUCCESS.getCode().equals(esbService.sendZns(znsRequest).getErrorCode());

                default:
                    return false;
            }
        } catch (Exception e) {
            log.error("Error sending {} noti for id {}: {}", channel, detail.getId(), e.getMessage(), e);
            return false;
        }
    }

    public Map<String, Object> sendNotificationWithPriority(CampainNotiDetail detail) {
        Map<String, Object> result = new HashMap<>();
        SystemConfig config = systemConfigRepository.findFirstByCodeAndStatus("PRIORITY_NOTI", RecordStatusEnum.ACTIVE.getVal()).orElse(null);
        if (config == null || StringUtils.isBlank(config.getStrVal())) {
            result.put("result", false);
            return result;
        }

        for (String channelStr : config.getStrVal().split(",")) {
            try {
                NotiChannelEnum channel = NotiChannelEnum.valueOf(channelStr.trim().toUpperCase());
                if (sendNotification(detail, channel)) {
                    result.put("result", false);
                    result.put("channel", channelStr);
                    return result;
                }
            } catch (Exception ex) {
                log.warn("Unsupported or failed channel {} for id {}: {}", channelStr, detail.getId(), ex.getMessage());
            }
        }

        result.put("result", false);
        return result;
    }
}
