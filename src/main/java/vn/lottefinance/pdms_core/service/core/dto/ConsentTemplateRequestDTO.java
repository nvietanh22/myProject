package vn.lottefinance.pdms_core.service.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ConsentTemplateRequestDTO {
    private String tempName;
    private String content;
    private String level1;
    private String level2;
    private String channel;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime modifyDate;
    private String status;
}
