package vn.lottefinance.pdms_core.service.core.dto;

import lombok.*;

import java.time.LocalDateTime;

public class RefreshTokenDTO {

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String refreshToken;
        private String userID;
        private LocalDateTime createdDate;
        private LocalDateTime endDate;
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String refreshToken;
        private String userID;
        private LocalDateTime createdDate;
        private LocalDateTime endDate;
    }
}
