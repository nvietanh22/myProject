package vn.lottefinance.pdms_core.web.rest;

import vn.lottefinance.pdms_core.service.core.dto.TokenDTO;
import vn.lottefinance.pdms_core.service.core.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    private final TokenService service;

    public TokenController(TokenService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TokenDTO.Response>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TokenDTO.Response> getById(@PathVariable Long id) {
        TokenDTO.Response dto = service.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<TokenDTO.Response> create(@RequestBody TokenDTO.Request request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TokenDTO.Response> update(@PathVariable Long id, @RequestBody TokenDTO.Request request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
