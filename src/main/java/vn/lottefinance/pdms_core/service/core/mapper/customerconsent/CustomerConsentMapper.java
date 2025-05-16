package vn.lottefinance.pdms_core.service.core.mapper.customerconsent;

import vn.lottefinance.pdms_core.domain.CustomerConsent;
import vn.lottefinance.pdms_core.service.core.dto.customerconsent.CustomerConsentDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerConsentMapper {
    public static CustomerConsentDTO.Response toResponse(CustomerConsent entity) {
        if (entity == null) return null;
        return CustomerConsentDTO.Response.builder()
                .id(entity.getId())
                .customerName(entity.getCustomerName())
                .idCardNumber(entity.getIdCardNumber())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .refId(entity.getRefId())
                .cifNumber(entity.getCifNumber())
                .createBy(entity.getCreateBy())
                .createDate(entity.getCreatedDate())
                .modifyBy(entity.getModifyBy())
                .modifyDate(entity.getModifyDate())
                .dob(entity.getDob())
                .channel(entity.getChannel())
                .issuedDate(entity.getIssuedDate())
                .issuedPlace(entity.getIssuedPlace())
                .cusComment(entity.getCusComment())
                .reference(entity.getReference())
                .evidence(entity.getEvidence())
                .consentLogs(!entity.getCustomerConsentLogs().isEmpty() ? entity.getCustomerConsentLogs().stream().map(CustomerConsentLogMapper::toResponsetDTO).collect(Collectors.toList()) : new ArrayList<>())
                .build();
    }

    public static CustomerConsent toEntity(CustomerConsentDTO.Request request) {
        if (request == null) return null;
        return CustomerConsent.builder()
                .customerName(request.getCustomerName())
                .idCardNumber(request.getIdCardNumber())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .refId(request.getRefId())
                .cifNumber(request.getCifNumber())
                .createBy(request.getCreateBy())
                .createdDate(request.getCreateDate())
                .modifyBy(request.getModifyBy())
                .modifyDate(request.getModifyDate())
                .dob(request.getDob())
                .channel(request.getChannel())
                .issuedDate(request.getIssuedDate())
                .issuedPlace(request.getIssuedPlace())
                .cusComment(request.getCusComment())
                .reference(request.getReference())
                .evidence(request.getEvidence())
                .build();
    }

    public static CustomerConsent toEntityFromResponse(CustomerConsentDTO.Response response) {
        if (response == null) return null;
        return CustomerConsent.builder()
                .id(response.getId())
                .customerName(response.getCustomerName())
                .idCardNumber(response.getIdCardNumber())
                .phoneNumber(response.getPhoneNumber())
                .email(response.getEmail())
                .refId(response.getRefId())
                .cifNumber(response.getCifNumber())
                .createBy(response.getCreateBy())
                .createdDate(response.getCreateDate())
                .modifyBy(response.getModifyBy())
                .modifyDate(response.getModifyDate())
                .dob(response.getDob())
                .channel(response.getChannel())
                .issuedDate(response.getIssuedDate())
                .issuedPlace(response.getIssuedPlace())
                .cusComment(response.getCusComment())
                .reference(response.getReference())
                .evidence(response.getEvidence())
                .build();
    }
}
