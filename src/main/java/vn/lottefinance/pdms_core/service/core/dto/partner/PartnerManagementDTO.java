package vn.lottefinance.pdms_core.service.core.dto.partner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class PartnerManagementDTO {
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder(toBuilder = true)
    public static class Request {
        private Long id;
        private String partnerName;
        private String partnerCode;
        private String taxCode; // Y|N
        private LocalDateTime createdDate;
        private String createdBy;
        private LocalDateTime modifyDate;
        private String modifyBy;
        private String additionalInfo;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder(toBuilder = true)
    public static class Response {
        private Long id;
        private String partnerName;
        private String partnerCode;
        private String taxCode; // Y|N
        private LocalDateTime createdDate;
        private String createdBy;
        private LocalDateTime modifyDate;
        private String modifyBy;
        private String additionalInfo;
    }
}
