package vn.lottefinance.pdms_core.service.core;

import vn.lottefinance.pdms_core.service.core.dto.customerconsent.CustomerConsentDTO;
import vn.lottefinance.pdms_core.service.core.dto.customerconsent.CustomerConsentLogDTO;

import java.util.List;

public interface CustomerConsentLogService {
    List<CustomerConsentLogDTO.Response> findAll();
    CustomerConsentLogDTO.Response findById(Long id);
    CustomerConsentLogDTO.Response create(CustomerConsentLogDTO.Request request);
    CustomerConsentLogDTO.Response update(Long id, CustomerConsentLogDTO.Request request);
    void delete(Long id);
}
