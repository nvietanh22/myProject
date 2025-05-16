package vn.lottefinance.pdms_core.service.core.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SystemConfigDTO {

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String code;
        private String name;
        private String dataType;
        private String strVal;
        private Integer intVal;
        private Boolean booleanVal;
        private Double doubleVal;
        private String createdBy;
        private LocalDateTime createdDate;
        private Integer status;
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String code;
        private String name;
        private String dataType;
        private String strVal;
        private Integer intVal;
        private Boolean booleanVal;
        private Double doubleVal;
        private String createdBy;
        private LocalDateTime createdDate;
        private Integer status;
    }
}
