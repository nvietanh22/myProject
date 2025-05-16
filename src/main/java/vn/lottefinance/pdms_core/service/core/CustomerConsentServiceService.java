package vn.lottefinance.pdms_core.service.core;

import vn.lottefinance.pdms_core.service.core.dto.customerconsent.CustomerConsentServiceDTO;

import java.util.List;

public interface CustomerConsentServiceService {
    List<CustomerConsentServiceDTO.Response> findAll();
    CustomerConsentServiceDTO.Response findById(Long id);
    CustomerConsentServiceDTO.Response create(CustomerConsentServiceDTO.Request request);
    CustomerConsentServiceDTO.Response update(Long id, CustomerConsentServiceDTO.Request request);
    void delete(Long id);
}
