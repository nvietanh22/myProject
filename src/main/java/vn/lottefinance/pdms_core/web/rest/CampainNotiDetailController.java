package vn.lottefinance.pdms_core.web.rest;

import vn.lottefinance.pdms_core.service.core.dto.campain.CampainNotiDetailDTO;
import vn.lottefinance.pdms_core.service.core.CampainNotiDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/campainNotiDetail")
public class CampainNotiDetailController {

    private final CampainNotiDetailService service;

    public CampainNotiDetailController(CampainNotiDetailService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CampainNotiDetailDTO.Response>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampainNotiDetailDTO.Response> getById(@PathVariable Long id) {
        CampainNotiDetailDTO.Response dto = service.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CampainNotiDetailDTO.Response> create(@RequestBody CampainNotiDetailDTO.Request request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampainNotiDetailDTO.Response> update(@PathVariable Long id, @RequestBody CampainNotiDetailDTO.Request request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
