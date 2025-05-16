package vn.lottefinance.pdms_core.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.lottefinance.pdms_core.config.JsonResponseBase;
import vn.lottefinance.pdms_core.service.core.PermissionService;
import vn.lottefinance.pdms_core.service.core.dto.permission.PermissionDTO;
import vn.lottefinance.pdms_core.service.core.dto.permission.PermissionRequestDto;
import vn.lottefinance.pdms_core.service.core.dto.permission.PermissionResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {

    private final PermissionService service;

    public PermissionController(PermissionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PermissionDTO.Response>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionDTO.Response> getById(@PathVariable Long id) {
        PermissionDTO.Response dto = service.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<JsonResponseBase<PermissionResponseDto>> create(@RequestBody PermissionRequestDto request) {
        return ResponseEntity.ok(service.create(request));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<PermissionDTO.Response> update(@PathVariable Long id, @RequestBody PermissionDTO.Request request) {
//        return ResponseEntity.ok(service.update(id, request));
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        service.delete(id);
//        return ResponseEntity.noContent().build();
//    }
}
