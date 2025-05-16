package vn.lottefinance.pdms_core.service.core.mapper.campainnoti;


import vn.lottefinance.pdms_core.domain.CampainNotiDetail;
import vn.lottefinance.pdms_core.service.core.dto.campain.CampainNotiDetailDTO;

public class CampainNotiDetailMapper {
    public static CampainNotiDetailDTO.Response toDTO(CampainNotiDetail entity) {
        return CampainNotiDetailDTO.Response.builder()
                .campainNotiId(entity.getCampainNoti() != null ? entity.getCampainNoti().getId() : null)
                .cifNumber(entity.getCifNumber())
                .creatDate(entity.getCreatedDate())
                .customerName(entity.getCustomerName())
                .notiChannel(entity.getNotiChannel())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .status(entity.getStatus())
                .build();
    }

    public static CampainNotiDetail toEntity(CampainNotiDetailDTO.Request request) {
        return CampainNotiDetail.builder()
                .cifNumber(request.getCifNumber())
                .customerName(request.getCustomerName())
                .notiChannel(request.getNotiChannel())
                .status(request.getStatus())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .idCardNumber(request.getIdCardNumber())
                .build();
    }
}
