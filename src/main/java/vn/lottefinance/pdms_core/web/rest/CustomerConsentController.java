package vn.lottefinance.pdms_core.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.lottefinance.pdms_core.config.JsonResponseBase;
import vn.lottefinance.pdms_core.config.SearchResult;
import vn.lottefinance.pdms_core.service.core.CustomerConsentService;
import vn.lottefinance.pdms_core.service.core.dto.customerconsent.CustomerConsentDTO;

import java.util.List;

@RestController
@RequestMapping("/api/admin/customerConsent")
public class CustomerConsentController {

    private final CustomerConsentService service;

    public CustomerConsentController(CustomerConsentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CustomerConsentDTO.Response>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerConsentDTO.Response> getById(@PathVariable Long id) {
        CustomerConsentDTO.Response dto = service.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<JsonResponseBase<CustomerConsentDTO.CustomerAcceptResponse>> create(@RequestBody CustomerConsentDTO.Request request) {
        return ResponseEntity.ok(service.created(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JsonResponseBase<CustomerConsentDTO.Response>> update(@PathVariable Long id, @RequestBody CustomerConsentDTO.Request request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<SearchResult<List<CustomerConsentDTO.Response>>> searchUser(@RequestBody CustomerConsentDTO.Search dto) {
        return ResponseEntity.ok(service.searching(dto));
    }
}
