package vn.lottefinance.pdms_core.service.core;

import vn.lottefinance.pdms_core.service.core.dto.campain.CampainNotiDetailDTO;
import vn.lottefinance.pdms_core.service.core.dto.partner.PartnerFormConfigDTO;

import java.util.List;

public interface PartnerFormConfigService {
    List<PartnerFormConfigDTO.Response> findAll();
    PartnerFormConfigDTO.Response findById(Long id);
    PartnerFormConfigDTO.Response create(PartnerFormConfigDTO.Request request);
    PartnerFormConfigDTO.Response update(Long id, PartnerFormConfigDTO.Request request);
    void delete(Long id);
}
