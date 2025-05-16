package vn.lottefinance.pdms_core.web.rest;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.lottefinance.pdms_core.common.BaseResponseDTO;
import vn.lottefinance.pdms_core.service.core.PartnerManagementService;
import vn.lottefinance.pdms_core.service.core.dto.partner.PartnerManagementDTO;

import java.util.List;

@RestController
@RequestMapping("/api/admin/partnermanagement")
@RequiredArgsConstructor
public class PartnermanagementController {

    private final PartnerManagementService service;

    @GetMapping
    public ResponseEntity<BaseResponseDTO<List<PartnerManagementDTO.Response>>> getAll() {
        return ResponseEntity.ok(BaseResponseDTO.<List<PartnerManagementDTO.Response>>builder()
                .data(service.findAll())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDTO<PartnerManagementDTO.Response>> getById(@PathVariable Long id) {
        PartnerManagementDTO.Response dto = service.findById(id);
        return dto != null ? ResponseEntity.ok(BaseResponseDTO.<PartnerManagementDTO.Response>builder()
                .data(dto)
                .build()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<BaseResponseDTO<PartnerManagementDTO.Response>> create(@RequestBody PartnerManagementDTO.Request request) {
        return ResponseEntity.ok(BaseResponseDTO.<PartnerManagementDTO.Response>builder()
                .data(service.create(request))
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseDTO<PartnerManagementDTO.Response>> update(@PathVariable Long id, @RequestBody PartnerManagementDTO.Request request) {
        return ResponseEntity.ok(BaseResponseDTO.<PartnerManagementDTO.Response>builder()
                .data(service.update(id, request))
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(BaseResponseDTO.builder().build());
    }
}