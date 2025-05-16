package vn.lottefinance.pdms_core.service.core.mapper.partner;

import vn.lottefinance.pdms_core.domain.PartnerFormConfig;
import vn.lottefinance.pdms_core.service.core.dto.partner.PartnerFormConfigDTO;

public class PartnerFormConfigMapper {
    public static PartnerFormConfig toEntity(PartnerFormConfigDTO.Request request) {
       return PartnerFormConfig.builder()
               .id(request.getId())
               .fieldCode(request.getFieldCode())
               .fieldLabel(request.getFieldLabel())
               .isRequired(request.getIsRequired())
               .isShow(request.getIsShow())
               .createdBy(request.getCreatedBy())
               .createdDate(request.getCreatedDate())
               .modifyBy(request.getModifyBy())
               .modifyDate(request.getModifyDate())
               .dataType(request.getDataType())
               .defaultValue(request.getDefaultValue())
               .build();
    }

    public static PartnerFormConfigDTO.Request toRequestDTO(PartnerFormConfig entity) {
        return PartnerFormConfigDTO.Request.builder()
                .id(entity.getId())
                .fieldCode(entity.getFieldCode())
                .fieldLabel(entity.getFieldLabel())
                .isRequired(entity.getIsRequired())
                .isShow(entity.getIsShow())
                .createdBy(entity.getCreatedBy())
                .createdDate(entity.getCreatedDate())
                .modifyBy(entity.getModifyBy())
                .modifyDate(entity.getModifyDate())
                .dataType(entity.getDataType())
                .defaultValue(entity.getDefaultValue())
                .build();
    }

    public static PartnerFormConfigDTO.Response toResponseDTO(PartnerFormConfig entity) {
        return PartnerFormConfigDTO.Response.builder()
                .id(entity.getId())
                .fieldCode(entity.getFieldCode())
                .fieldLabel(entity.getFieldLabel())
                .isRequired(entity.getIsRequired())
                .isShow(entity.getIsShow())
                .createdBy(entity.getCreatedBy())
                .createdDate(entity.getCreatedDate())
                .modifyBy(entity.getModifyBy())
                .modifyDate(entity.getModifyDate())
                .dataType(entity.getDataType())
                .defaultValue(entity.getDefaultValue())
                .build();
    }
}
