package vn.lottefinance.pdms_core.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.lottefinance.pdms_core.common.BaseResponseDTO;
import vn.lottefinance.pdms_core.service.core.PartnerAttachmentService;
import vn.lottefinance.pdms_core.service.core.PartnerManagementService;
import vn.lottefinance.pdms_core.service.core.dto.partner.PartnerAttachmentDTO;

@RestController
@RequestMapping("/api/admin/partner-attachment")
@RequiredArgsConstructor
public class PartnerAttachmentController {
    private final PartnerAttachmentService service;

    @PostMapping
    public ResponseEntity<BaseResponseDTO<PartnerAttachmentDTO.Response>> create(@RequestParam(name = "file")MultipartFile file,
                                                                                 @RequestParam(name = "fileName") String fileName,
                                                                                 @RequestParam(name = "partnerId") Long partnerId ) {

        PartnerAttachmentDTO.Request request = PartnerAttachmentDTO.Request.builder()
                .fileName(fileName)
                .build();
        return ResponseEntity.ok(BaseResponseDTO.<PartnerAttachmentDTO.Response>builder()
                .data(service.create(request, partnerId, file))
                .build());
    }

}
