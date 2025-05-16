package vn.lottefinance.pdms_core.service.core;

import vn.lottefinance.pdms_core.service.core.dto.partner.PartnerFormConfigDTO;
import vn.lottefinance.pdms_core.service.core.dto.partner.PartnerManagementDTO;

import java.util.List;

public interface PartnerManagementService {
    List<PartnerManagementDTO.Response> findAll();
    PartnerManagementDTO.Response findById(Long id);
    PartnerManagementDTO.Response create(PartnerManagementDTO.Request request);
    PartnerManagementDTO.Response update(Long id, PartnerManagementDTO.Request request);
    void delete(Long id);
}
