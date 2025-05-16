package vn.lottefinance.pdms_core.service.core.dto;

import lombok.*;

import java.time.LocalDateTime;

public class TokenDTO {

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String token;
        private String userID;
        private String Status;
        private LocalDateTime createdDate;
        private LocalDateTime endDate;
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String token;
        private String userID;
        private String Status;
        private LocalDateTime createdDate;
        private LocalDateTime endDate;
    }
}
