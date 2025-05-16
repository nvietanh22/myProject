package vn.lottefinance.pdms_core.web.rest;

import vn.lottefinance.pdms_core.service.core.dto.RefreshTokenDTO;
import vn.lottefinance.pdms_core.service.core.RefreshTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/refreshToken")
public class RefreshTokenController {

    private final RefreshTokenService service;

    public RefreshTokenController(RefreshTokenService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RefreshTokenDTO.Response>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefreshTokenDTO.Response> getById(@PathVariable Long id) {
        RefreshTokenDTO.Response dto = service.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<RefreshTokenDTO.Response> create(@RequestBody RefreshTokenDTO.Request request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RefreshTokenDTO.Response> update(@PathVariable Long id, @RequestBody RefreshTokenDTO.Request request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
