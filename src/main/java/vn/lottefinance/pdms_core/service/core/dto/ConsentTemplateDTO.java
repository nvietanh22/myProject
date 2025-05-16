package vn.lottefinance.pdms_core.service.core.dto;

import lombok.*;

import java.time.LocalDateTime;

public class ConsentTemplateDTO {

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        private String content;
        private String level1;
        private String level2;
        private String channel;
        private LocalDateTime createdDate;
        private String createdBy;
        private LocalDateTime modifyDate;
        private String status;
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String content;
        private String level1;
        private String level2;
        private String channel;
        private LocalDateTime createdDate;
        private String createdBy;
        private LocalDateTime modifyDate;
        private String status;
    }
}
