package vn.lottefinance.pdms_core.web.rest.pub;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.lottefinance.pdms_core.common.BaseResponseDTO;
import vn.lottefinance.pdms_core.domain.CampainNotiDetail;
import vn.lottefinance.pdms_core.domain.CustomerConsentLog;
import vn.lottefinance.pdms_core.domain.SystemConfig;
import vn.lottefinance.pdms_core.enums.CampainNotiStatusEnum;
import vn.lottefinance.pdms_core.enums.NotiChannelEnum;
import vn.lottefinance.pdms_core.enums.RecordStatusEnum;
import vn.lottefinance.pdms_core.repository.CustomerConsentLogRepository;
import vn.lottefinance.pdms_core.repository.SystemConfigRepository;
import vn.lottefinance.pdms_core.service.core.CustomerConsentService;
import vn.lottefinance.pdms_core.service.core.NotificationCustomerProcessService;
import vn.lottefinance.pdms_core.service.core.dto.customerconsent.CustomerConsentDTO;
import vn.lottefinance.pdms_core.service.core.mapper.customerconsent.CustomerConsentMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

@RequestMapping("/api/public/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final CustomerConsentService customerConsentService;
    private final NotificationCustomerProcessService notificationService;
    private final CustomerConsentLogRepository customerConsentLogRepository;
    private final SystemConfigRepository systemConfigRepository;

    @PostMapping("/add")
    public ResponseEntity<BaseResponseDTO<CustomerConsentDTO.Response>> create(@RequestBody CustomerConsentDTO.Request request) {
        CustomerConsentDTO.Response customerConsent = customerConsentService.findByIdCard(request.getIdCardNumber());
        SystemConfig channelNoti = systemConfigRepository.findFirstByCodeAndStatus("CHANNELS_NOTICE", RecordStatusEnum.ACTIVE.getVal()).orElse(null);

        CustomerConsentDTO.Response response = null;
        if (customerConsent == null) {
            customerConsent = customerConsentService.create(request);
        }

        String[] channelNotiArr = channelNoti.getStrVal().split(",");
        boolean send = false;
        String channelSendNoti = null;

        if (Arrays.asList(channelNotiArr).contains(request.getSourceData())) { // channel se gui noti
            CampainNotiDetail campainNotiDetail = CampainNotiDetail.builder()
                    .customerName(customerConsent.getCustomerName())
                    .idCardNumber(customerConsent.getIdCardNumber())
                    .cifNumber(customerConsent.getCifNumber())
                    .phoneNumber(customerConsent.getPhoneNumber())
                    .notiChannel(customerConsent.getNotiChannel())
                    .email(customerConsent.getEmail())
                    .build();


            if (!StringUtils.isEmpty(response.getNotiChannel())) {
                NotiChannelEnum channel = NotiChannelEnum.valueOf(campainNotiDetail.getNotiChannel());
                send = notificationService.sendNotification(campainNotiDetail, channel);
                channelSendNoti = campainNotiDetail.getNotiChannel();
            } else {
                Map<String, Object> resultSend = notificationService.sendNotificationWithPriority(campainNotiDetail);
                send =  (boolean)resultSend.get("result");
                channelSendNoti = (String)resultSend.get("channel");
            }
        } else { // channel khong gui noti, chi ghi nhan
            send = true;
            channelSendNoti = "SYSTEM";
        }

        CustomerConsentLog customerConsentLog = CustomerConsentLog.builder()
                .customerConsent(CustomerConsentMapper.toEntityFromResponse(customerConsent))
                .createBy("admin")
                .createdDate(LocalDateTime.now())
                .notiContent(request.getNotiContent())
                .customerName(customerConsent.getCustomerName())
                .sourceChannel(request.getSourceData())
                .notiChannel(channelSendNoti)
                .status(send ? CampainNotiStatusEnum.SUCCESS.getCode() : CampainNotiStatusEnum.ERROR.getCode())
                .build();
        customerConsentLogRepository.save(customerConsentLog);
        return ResponseEntity.ok(BaseResponseDTO.<CustomerConsentDTO.Response>builder()
                .data(response)
                .build());
    }

    @PostMapping("/inquiry")
    public ResponseEntity<BaseResponseDTO<CustomerConsentDTO.Response>> inquiry(@Valid @RequestBody CustomerConsentDTO.InquiryRequest request) {
        return ResponseEntity.ok(BaseResponseDTO.<CustomerConsentDTO.Response>builder()
                .data(customerConsentService.inquiry(request))
                .build());
    }


}
