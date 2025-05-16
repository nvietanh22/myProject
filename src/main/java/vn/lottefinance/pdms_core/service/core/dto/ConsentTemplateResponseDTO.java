package vn.lottefinance.pdms_core.service.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.lottefinance.pdms_core.domain.ConsentTemplate;
import vn.lottefinance.pdms_core.service.core.dto.role.RoleResponseDto;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ConsentTemplateResponseDTO {
    private Long id;
    private String tempName;
    private String content;
    private String level1;
    private String level2;
    private String channel;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime modifyDate;
    private String status;


    public static ConsentTemplateResponseDTO from(ConsentTemplate template){
        return ConsentTemplateResponseDTO.builder()
                .tempName(template.getTempName())
                .content(template.getContent())
                .level1(template.getLevel1())
                .level2(template.getLevel2())
                .channel(template.getChannel())
                .createdDate(template.getCreatedDate())
                .createdBy(template.getCreatedBy())
                .modifyDate(template.getModifyDate())
                .status(template.getStatus())
                .build();
    }
}
