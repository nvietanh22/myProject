package vn.lottefinance.pdms_core.service.core;

import vn.lottefinance.pdms_core.config.JsonResponseBase;
import vn.lottefinance.pdms_core.config.SearchResult;
import vn.lottefinance.pdms_core.service.core.dto.customerconsent.CustomerConsentDTO;

import java.util.List;

public interface CustomerConsentService {
    List<CustomerConsentDTO.Response> findAll();
    CustomerConsentDTO.Response findById(Long id);
    CustomerConsentDTO.Response findByIdCard(String idCard);
    CustomerConsentDTO.Response create(CustomerConsentDTO.Request request);
    JsonResponseBase<CustomerConsentDTO.Response> update(Long id, CustomerConsentDTO.Request request);
    JsonResponseBase<CustomerConsentDTO.CustomerAcceptResponse>  created(CustomerConsentDTO.Request request);
    CustomerConsentDTO.Response inquiry(CustomerConsentDTO.InquiryRequest request);
    void delete(Long id);
    SearchResult<List<CustomerConsentDTO.Response>> searching(CustomerConsentDTO.Search search);
}
