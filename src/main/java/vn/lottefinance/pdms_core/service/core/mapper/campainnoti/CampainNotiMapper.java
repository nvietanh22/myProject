package vn.lottefinance.pdms_core.service.core.mapper.campainnoti;

import vn.lottefinance.pdms_core.domain.CampainNoti;
import vn.lottefinance.pdms_core.service.core.dto.campain.CampainNotiDTO;

public class CampainNotiMapper {
    public static CampainNoti toEntity(CampainNotiDTO.Request request) {
        return CampainNoti.builder()
                .totalRecord(request.getTotalRecord())
                .fileName(request.getFileName())
                .createdBy(request.getCreatedBy())
                .createdDate(request.getCreatedDate())
                .modifyBy(request.getModifyBy())
                .modifyDate(request.getModifyDate())
                .status(request.getStatus())
                .build();
    }
}
