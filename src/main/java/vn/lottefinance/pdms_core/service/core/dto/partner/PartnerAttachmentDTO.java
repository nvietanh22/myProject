package vn.lottefinance.pdms_core.service.core.dto.partner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class PartnerAttachmentDTO {
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder(toBuilder = true)
    public static class Request {
        private Long id;
        private String fileName;
        private String fileUrl;
        private PartnerManagementDTO.Request partner; // Y|N
        private LocalDateTime createdDate;
        private String createdBy;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder(toBuilder = true)
    public static class Response {
        private Long id;
        private String fileName;
        private String fileUrl;
        private PartnerManagementDTO.Response partner;
        private LocalDateTime createdDate;
        private String createdBy;
    }
}
