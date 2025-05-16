package vn.lottefinance.pdms_core.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.lottefinance.pdms_core.common.BaseResponseDTO;
import vn.lottefinance.pdms_core.service.core.CustomerConsentServiceService;
import vn.lottefinance.pdms_core.service.core.PartnerFormConfigService;
import vn.lottefinance.pdms_core.service.core.dto.customerconsent.CustomerConsentDTO;
import vn.lottefinance.pdms_core.service.core.dto.customerconsent.CustomerConsentServiceDTO;
import vn.lottefinance.pdms_core.service.core.dto.partner.PartnerFormConfigDTO;

import java.util.List;

@RestController
@RequestMapping("/api/admin/partner-config")
public class PartnerFormConfigController {

    private final PartnerFormConfigService service;

    public PartnerFormConfigController(PartnerFormConfigService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<BaseResponseDTO<List<PartnerFormConfigDTO.Response>>> getAll() {
        return ResponseEntity.ok(BaseResponseDTO.<List<PartnerFormConfigDTO.Response>>builder()
                .data(service.findAll())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDTO<PartnerFormConfigDTO.Response>> getById(@PathVariable Long id) {
        PartnerFormConfigDTO.Response dto = service.findById(id);
        return dto != null ? ResponseEntity.ok(BaseResponseDTO.<PartnerFormConfigDTO.Response>builder()
                .data(dto)
                .build()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<BaseResponseDTO<PartnerFormConfigDTO.Response>> create(@RequestBody PartnerFormConfigDTO.Request request) {
        return ResponseEntity.ok(BaseResponseDTO.<PartnerFormConfigDTO.Response>builder()
                .data(service.create(request))
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseDTO<PartnerFormConfigDTO.Response>> update(@PathVariable Long id, @RequestBody PartnerFormConfigDTO.Request request) {
        return ResponseEntity.ok(BaseResponseDTO.<PartnerFormConfigDTO.Response>builder()
                .data(service.update(id, request))
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(BaseResponseDTO.builder().build());
    }
}
