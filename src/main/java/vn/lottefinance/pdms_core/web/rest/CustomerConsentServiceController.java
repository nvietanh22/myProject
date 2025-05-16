package vn.lottefinance.pdms_core.web.rest;

import vn.lottefinance.pdms_core.service.core.dto.customerconsent.CustomerConsentServiceDTO;
import vn.lottefinance.pdms_core.service.core.CustomerConsentServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/customerConsentService")
public class CustomerConsentServiceController {

    private final CustomerConsentServiceService service;

    public CustomerConsentServiceController(CustomerConsentServiceService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CustomerConsentServiceDTO.Response>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerConsentServiceDTO.Response> getById(@PathVariable Long id) {
        CustomerConsentServiceDTO.Response dto = service.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CustomerConsentServiceDTO.Response> create(@RequestBody CustomerConsentServiceDTO.Request request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerConsentServiceDTO.Response> update(@PathVariable Long id, @RequestBody CustomerConsentServiceDTO.Request request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
