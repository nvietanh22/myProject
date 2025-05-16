package vn.lottefinance.pdms_core.service.core.dto.customerconsent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class CustomerConsentLogDTO {
    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private Long id;
        private String customerName;
        private String idCardNumber;
        private String phoneNumber;
        private String sourceChannel;
        private String notiChannel;
        private String notiContent;
        private String createBy;
        private LocalDateTime createdDate;
        private String status;
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
        private String sourceChannel;
        private String notiChannel;
        private String notiContent;
        private String createBy;
        private LocalDateTime createdDate;
        private String status;
    }
}
