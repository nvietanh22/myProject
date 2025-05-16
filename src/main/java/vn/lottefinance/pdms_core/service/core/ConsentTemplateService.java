package vn.lottefinance.pdms_core.service.core;

import vn.lottefinance.pdms_core.config.JsonResponseBase;
import vn.lottefinance.pdms_core.service.core.dto.ConsentTemplateDTO;
import vn.lottefinance.pdms_core.service.core.dto.ConsentTemplateRequestDTO;
import vn.lottefinance.pdms_core.service.core.dto.ConsentTemplateResponseDTO;

import java.util.List;

public interface ConsentTemplateService {
    List<ConsentTemplateDTO.Response> findAll();
    ConsentTemplateDTO.Response findById(Long id);
    JsonResponseBase<ConsentTemplateResponseDTO> create(ConsentTemplateRequestDTO request);
    JsonResponseBase<ConsentTemplateResponseDTO> update(Long id, ConsentTemplateRequestDTO request);
    void delete(Long id);
}
