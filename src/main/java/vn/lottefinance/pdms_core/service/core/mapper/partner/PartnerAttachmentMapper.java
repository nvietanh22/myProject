package vn.lottefinance.pdms_core.service.core.mapper.partner;

import vn.lottefinance.pdms_core.domain.PartnerAttachment;
import vn.lottefinance.pdms_core.service.core.dto.partner.PartnerAttachmentDTO;

public class PartnerAttachmentMapper {
    public static PartnerAttachment toEntityFromReq(PartnerAttachmentDTO.Request request) {
        return PartnerAttachment.builder()
                .id(request.getId())
                .partnerManagement(PartnerManagementMapper.toEntity(request.getPartner()))
                .fileName(request.getFileName())
                .fileUrl(request.getFileUrl())
                .createdBy(request.getCreatedBy())
                .createdDate(request.getCreatedDate())
                .build();
    }

    public static PartnerAttachment toEntityFromRes(PartnerAttachmentDTO.Response response) {
        return PartnerAttachment.builder()
                .id(response.getId())
                .partnerManagement(PartnerManagementMapper.toEntityFromRes(response.getPartner()))
                .fileName(response.getFileName())
                .fileUrl(response.getFileUrl())
                .createdBy(response.getCreatedBy())
                .createdDate(response.getCreatedDate())
                .build();
    }

    public static PartnerAttachmentDTO.Response toRespone(PartnerAttachment entity) {
        return PartnerAttachmentDTO.Response.builder()
                .id(entity.getId())
                .partner(PartnerManagementMapper.toResponseDTO(entity.getPartnerManagement()))
                .fileName(entity.getFileName())
                .fileUrl(entity.getFileUrl())
                .createdBy(entity.getCreatedBy())
                .createdDate(entity.getCreatedDate())
                .build();
    }
}
