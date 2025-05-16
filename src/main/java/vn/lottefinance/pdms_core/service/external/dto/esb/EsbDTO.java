package vn.lottefinance.pdms_core.service.external.dto.esb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

public class EsbDTO {

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmailRequest {
        private String mailTo;
        private Integer templateId;
        private String title;
        private Map<String, String> data;
        private Map<String, String> attachment;
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmailResponse {
        private String errorCode;
        private String errorMsg;
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ZnsRequest {
        private String phone;
        private String channel;
        private String code;
        private Map<String, String> param;
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ZnsResponse {
        private String errorCode;
        private String errorMsg;
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SmsRequest {
        private String phone;
        private String channel;
        private String message;
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SmsResponse {
        private String errorCode;
        private String errorMsg;
    }
}
