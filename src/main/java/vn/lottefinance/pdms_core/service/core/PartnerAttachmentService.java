package vn.lottefinance.pdms_core.service.core;

import org.springframework.web.multipart.MultipartFile;
import vn.lottefinance.pdms_core.service.core.dto.partner.PartnerAttachmentDTO;
import vn.lottefinance.pdms_core.service.core.dto.partner.PartnerFormConfigDTO;

import java.util.List;

public interface PartnerAttachmentService {
    List<PartnerAttachmentDTO.Response> findAll();
    PartnerAttachmentDTO.Response findById(Long id);
    PartnerAttachmentDTO.Response create(PartnerAttachmentDTO.Request request, Long partnerId, MultipartFile file);
    PartnerAttachmentDTO.Response update(Long id, PartnerAttachmentDTO.Request request);
    void delete(Long id);
}
