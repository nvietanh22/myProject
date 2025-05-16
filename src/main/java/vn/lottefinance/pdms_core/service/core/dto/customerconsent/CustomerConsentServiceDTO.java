package vn.lottefinance.pdms_core.service.core.dto.customerconsent;

import lombok.*;

import java.time.LocalDateTime;

public class CustomerConsentServiceDTO {

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private Long customerConsentId;
        private Long serviceId;
        private LocalDateTime createdDate;
        private String createdBy;
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private Long customerConsentId;
        private Long serviceId;
        private LocalDateTime createdDate;
        private String createdBy;
    }
}
