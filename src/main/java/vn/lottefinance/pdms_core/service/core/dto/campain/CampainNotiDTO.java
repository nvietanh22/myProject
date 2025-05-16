package vn.lottefinance.pdms_core.service.core.dto.campain;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class CampainNotiDTO {

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotBlank
        private String fileName;
        @NotBlank
        private Long totalRecord;
        private String createdBy;
        private LocalDateTime createdDate;
        private LocalDateTime modifyDate;
        private String modifyBy ;
        private String status;
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String fileName;
        private Long totalRecord;
        private String createdBy;
        private LocalDateTime createdDate;
        private LocalDateTime modifyDate;
        private String modifyBy ;
        private String status;
        private List<CampainNotiDetailDTO> campainNotiDetailDTOList;
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UploadFileResponse {
        private CampainNotiDTO.Response campainNotiDTO;
        private List<CampainNotiDetailDTO.Response> campainNotiDetailDTOList;
        private List<CampainNotiDetailDTO.Response> campainNotiDetailDTOErrorList;
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddRequest {
        private CampainNotiDTO.Request campainNoti;
        private List<CampainNotiDetailDTO.Request> campainNotiDetailList;
    }
}
