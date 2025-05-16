package vn.lottefinance.pdms_core.service.core.dto.partner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class PartnerFormConfigDTO {
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder(toBuilder = true)
    public static class Request {
        private Long id;
        private String fieldCode;
        private String fieldLabel;
        private String isRequired; // Y|N
        private String isShow; // Y|N
        private LocalDateTime createdDate;
        private String createdBy;
        private LocalDateTime modifyDate;
        private String modifyBy;
        private String dataType;
        private String defaultValue;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder(toBuilder = true)
    public static class Response {
        private Long id;
        private String fieldCode;
        private String fieldLabel;
        private String isRequired; // Y|N
        private String isShow; // Y|N
        private LocalDateTime createdDate;
        private String createdBy;
        private LocalDateTime modifyDate;
        private String modifyBy;
        private String dataType;
        private String defaultValue;
    }
}
