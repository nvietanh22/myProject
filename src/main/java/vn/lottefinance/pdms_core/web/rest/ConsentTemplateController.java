package vn.lottefinance.pdms_core.web.rest;

import vn.lottefinance.pdms_core.config.JsonResponseBase;
import vn.lottefinance.pdms_core.service.core.dto.ConsentTemplateDTO;
import vn.lottefinance.pdms_core.service.core.ConsentTemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.lottefinance.pdms_core.service.core.dto.ConsentTemplateRequestDTO;
import vn.lottefinance.pdms_core.service.core.dto.ConsentTemplateResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/api/consentTemplate")
public class ConsentTemplateController {

    private final ConsentTemplateService service;

    public ConsentTemplateController(ConsentTemplateService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ConsentTemplateDTO.Response>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsentTemplateDTO.Response> getById(@PathVariable Long id) {
        ConsentTemplateDTO.Response dto = service.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<JsonResponseBase<ConsentTemplateResponseDTO>> create(@RequestBody ConsentTemplateRequestDTO request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JsonResponseBase<ConsentTemplateResponseDTO>> update(@PathVariable Long id, @RequestBody ConsentTemplateRequestDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
