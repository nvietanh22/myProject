package vn.lottefinance.pdms_core.web.rest;

import vn.lottefinance.pdms_core.config.JsonResponseBase;
import vn.lottefinance.pdms_core.config.SearchResult;
import vn.lottefinance.pdms_core.service.core.dto.role.*;
import vn.lottefinance.pdms_core.service.core.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO.Response>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO.Response> getById(@PathVariable Long id) {
        RoleDTO.Response dto = service.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<JsonResponseBase<RoleResponseDto>> create(@RequestBody RoleRequestDto request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PostMapping(value = "/permission/update")
    public ResponseEntity<JsonResponseBase<RoleResponseDto>> updatePermission(@RequestBody RoleUpdatePermissionRequestDto roleUpdatePermissionRequestDto) {
        return ResponseEntity.ok(service.updatePermissionToRole(roleUpdatePermissionRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-permission")
    public ResponseEntity<List<String>> getPermission(@RequestParam(name = "authorities", required = false) List<String> authorities)
            throws URISyntaxException {
        if (authorities == null || authorities.isEmpty()) {
            return ResponseEntity.ok(new ArrayList<>());
        }

        return ResponseEntity.ok(service.getPermissionByRoles(authorities));
    }
    @PostMapping(value = "/search")
    public ResponseEntity<SearchResult<List<RoleResponseDto>>> searchRole(@RequestBody RoleSearchRequestDto roleSearchRequestDto) {
        return ResponseEntity.ok(service.listAllActiveRole(roleSearchRequestDto));
    }
}
