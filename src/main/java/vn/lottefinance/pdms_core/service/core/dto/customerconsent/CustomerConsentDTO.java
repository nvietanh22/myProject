package vn.lottefinance.pdms_core.service.core.dto.customerconsent;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import vn.lottefinance.pdms_core.config.SearchRequestBase;
import vn.lottefinance.pdms_core.domain.CustomerConsent;
import vn.lottefinance.pdms_core.enums.ConsentSourceEnum;

import java.time.LocalDateTime;
import java.util.List;

public class CustomerConsentDTO {

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String customerName;
        private String idCardNumber;
        private String phoneNumber;
        private String email;
        private String statusNoti;
        private String sourceData;
        private String sourceStatusNoti;
        private String notiChannel;
        private String refId;
        private String cifNumber;
        private String notiContent;
        private String createBy;
        private String modifyBy;
        private LocalDateTime createDate;
        private LocalDateTime modifyDate;
        private LocalDateTime dob;
        private String channel;
        private LocalDateTime issuedDate;
        private String issuedPlace;
        private String cusComment;
        private String reference;
        private String evidence;
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String customerName;
        private String idCardNumber;
        private String phoneNumber;
        private String email;
        private String statusNoti;
        private String sourceData;
        private String sourceStatusNoti;
        private String notiChannel;
        private String refId;
        private String cifNumber;
        private String notiContent;
        private String createBy;
        private String modifyBy;
        private LocalDateTime createDate;
        private LocalDateTime modifyDate;
        private LocalDateTime dob;
        private String channel;
        private LocalDateTime issuedDate;
        private String issuedPlace;
        private String cusComment;
        private String reference;
        private String evidence;
        private List<CustomerConsentLogDTO.Response> consentLogs;

        public static Response from(CustomerConsent consent){
            return Response.builder()
                    .customerName(consent.getCustomerName())
                    .idCardNumber(consent.getIdCardNumber())
                    .phoneNumber(consent.getPhoneNumber())
                    .email(consent.getEmail())
                    .refId(consent.getRefId())
                    .cifNumber(consent.getCifNumber())
                    .createBy(consent.getCreateBy())
                    .modifyBy(consent.getModifyBy())
                    .createDate(consent.getCreatedDate())
                    .modifyDate(consent.getModifyDate())
                    .dob(consent.getDob())
                    .channel(consent.getChannel())
                    .issuedDate(consent.getIssuedDate())
                    .issuedPlace(consent.getIssuedPlace())
                    .cusComment(consent.getCusComment())
                    .reference(consent.getReference())
                    .evidence(consent.getEvidence())
                    .build();
        }
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InquiryRequest {
        private String cifNumber;
        @NotBlank
        private String idCardNumber;
        @NotBlank
        private String phoneNumber;
    }


    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Search extends SearchRequestBase {
        private String customerName;
        private String idCardNumber;
        private String phoneNumber;
        private LocalDateTime createdDateFrom;
        private LocalDateTime createdDateTo;
        private LocalDateTime modifyDate;

    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerAcceptResponse{
        private String customerName;
        private LocalDateTime dob;
        private String idCardNumber;
        private LocalDateTime issuedDate;
        private String issuedPlace;
        private String phoneNumber;
        private String status;
        public static CustomerAcceptResponse from(CustomerConsent consent){
            return CustomerAcceptResponse.builder()
                    .customerName(consent.getCustomerName())
                    .idCardNumber(consent.getIdCardNumber())
                    .phoneNumber(consent.getPhoneNumber())
                    .dob(consent.getDob())
                    .issuedDate(consent.getIssuedDate())
                    .issuedPlace(consent.getIssuedPlace())
                    .build();
        }
    }
}
