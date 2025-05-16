package vn.lottefinance.pdms_core.service.core.dto.campain;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

public class CampainNotiDetailDTO {

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private Long campainNotiId;
        private String cifNumber;
        @NotBlank
        private String idCardNumber;
        @NotBlank
        private String customerName;
        @NotBlank
        private String phoneNumber;
        @NotBlank
        private String email;
        private String notiChannel;
        private LocalDateTime creatDate;
        private String status;
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private Long campainNotiId;
        private String cifNumber;
        private String idCardNumber;
        private String customerName;
        private String phoneNumber;
        private String email;
        private String notiChannel;
        private LocalDateTime creatDate;
        private String status;
    }
}
