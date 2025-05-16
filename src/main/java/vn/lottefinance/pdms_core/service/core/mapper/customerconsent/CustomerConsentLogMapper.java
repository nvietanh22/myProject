package vn.lottefinance.pdms_core.service.core.mapper.customerconsent;


import vn.lottefinance.pdms_core.domain.CustomerConsent;
import vn.lottefinance.pdms_core.domain.CustomerConsentLog;
import vn.lottefinance.pdms_core.service.core.dto.customerconsent.CustomerConsentLogDTO;

public class CustomerConsentLogMapper {
    public static CustomerConsentLog toEntity(CustomerConsentLogDTO.Request request) {
        return CustomerConsentLog.builder()
                .id(request.getId())
                .customerName(request.getCustomerName())
                .createBy(request.getCreateBy())
                .createdDate(request.getCreatedDate())
                .notiChannel(request.getNotiChannel())
                .notiContent(request.getNotiContent())
                .sourceChannel(request.getSourceChannel())
                .status(request.getStatus())
                .build();
    }

   public static CustomerConsentLogDTO.Request toRequestDTO(CustomerConsentLog entity) {
        return CustomerConsentLogDTO.Request.builder()
                .id(entity.getId())
                .idCardNumber(entity.getCustomerConsent().getIdCardNumber())
                .customerName(entity.getCustomerName())
                .createBy(entity.getCreateBy())
                .createdDate(entity.getCreatedDate())
                .notiChannel(entity.getNotiChannel())
                .notiContent(entity.getNotiContent())
                .sourceChannel(entity.getSourceChannel())
                .status(entity.getStatus())
                .build();
    }

    public static CustomerConsentLogDTO.Response toResponsetDTO(CustomerConsentLog entity) {
        return CustomerConsentLogDTO.Response.builder()
                .id(entity.getId())
                .idCardNumber(entity.getCustomerConsent().getIdCardNumber())
                .customerName(entity.getCustomerName())
                .createBy(entity.getCreateBy())
                .createdDate(entity.getCreatedDate())
                .notiChannel(entity.getNotiChannel())
                .notiContent(entity.getNotiContent())
                .sourceChannel(entity.getSourceChannel())
                .status(entity.getStatus())
                .build();
    }
}
