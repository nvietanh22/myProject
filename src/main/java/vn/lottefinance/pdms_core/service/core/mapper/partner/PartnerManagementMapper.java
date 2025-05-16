package vn.lottefinance.pdms_core.service.core.mapper.partner;

import vn.lottefinance.pdms_core.domain.PartnerFormConfig;
import vn.lottefinance.pdms_core.domain.PartnerManagement;
import vn.lottefinance.pdms_core.service.core.dto.partner.PartnerFormConfigDTO;
import vn.lottefinance.pdms_core.service.core.dto.partner.PartnerManagementDTO;

public class PartnerManagementMapper {
    public static PartnerManagement toEntity(PartnerManagementDTO.Request request) {
        return PartnerManagement.builder()
                .id(request.getId())
                .partnerCode(request.getPartnerCode())
                .partnerName(request.getPartnerName())
                .taxCode(request.getTaxCode())
                .createdBy(request.getCreatedBy())
                .createdDate(request.getCreatedDate())
                .modifyBy(request.getModifyBy())
                .modifyDate(request.getModifyDate())
                .additionalInfo(request.getAdditionalInfo())
                .build();
    }

    public static PartnerManagement toEntityFromRes(PartnerManagementDTO.Response response) {
        return PartnerManagement.builder()
                .id(response.getId())
                .partnerCode(response.getPartnerCode())
                .partnerName(response.getPartnerName())
                .taxCode(response.getTaxCode())
                .createdBy(response.getCreatedBy())
                .createdDate(response.getCreatedDate())
                .modifyBy(response.getModifyBy())
                .modifyDate(response.getModifyDate())
                .additionalInfo(response.getAdditionalInfo())
                .build();
    }

    public static PartnerManagementDTO.Request toRequestDTO(PartnerManagement entity) {
        return PartnerManagementDTO.Request.builder()
                .id(entity.getId())
                .partnerCode(entity.getPartnerCode())
                .partnerName(entity.getPartnerName())
                .taxCode(entity.getTaxCode())
                .createdBy(entity.getCreatedBy())
                .createdDate(entity.getCreatedDate())
                .modifyBy(entity.getModifyBy())
                .modifyDate(entity.getModifyDate())
                .additionalInfo(entity.getAdditionalInfo())
                .build();
    }

    public static PartnerManagementDTO.Response toResponseDTO(PartnerManagement entity) {
        return PartnerManagementDTO.Response.builder()
                .id(entity.getId())
                .partnerCode(entity.getPartnerCode())
                .partnerName(entity.getPartnerName())
                .taxCode(entity.getTaxCode())
                .createdBy(entity.getCreatedBy())
                .createdDate(entity.getCreatedDate())
                .modifyBy(entity.getModifyBy())
                .modifyDate(entity.getModifyDate())
                .additionalInfo(entity.getAdditionalInfo())
                .build();
    }
}
