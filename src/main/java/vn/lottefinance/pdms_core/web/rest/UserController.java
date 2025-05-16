package vn.lottefinance.pdms_core.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.lottefinance.pdms_core.common.BaseResponseDTO;
import vn.lottefinance.pdms_core.config.JsonResponseBase;
import vn.lottefinance.pdms_core.exception.BaseException;
import vn.lottefinance.pdms_core.service.core.UserService;
import vn.lottefinance.pdms_core.service.core.dto.user.UserDTO;
import vn.lottefinance.pdms_core.service.core.dto.user.UserRequestDto;
import vn.lottefinance.pdms_core.service.core.dto.user.UserResponseDto;
import vn.lottefinance.pdms_core.service.core.dto.user.UserUpdateRoleRequestDto;
import vn.lottefinance.pdms_core.util.BasePagingResponse;
import vn.lottefinance.pdms_core.util.SecurityUtils;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<BasePagingResponse<UserDTO.Response>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc")String[] sort
            ) {
        Sort sortOrder = Sort.by(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, size, sortOrder);

        BasePagingResponse<UserDTO.Response> response = userService.findAll(pageable);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO.Response> getById(@PathVariable Long id) {
        UserDTO.Response dto = userService.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<JsonResponseBase<UserResponseDto>> create(@RequestBody UserRequestDto request) throws BaseException {
        return ResponseEntity.ok(userService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JsonResponseBase<UserResponseDto>> update(@PathVariable Long id, @RequestBody UserRequestDto request) {
        return ResponseEntity.ok(userService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/role/update")
    public ResponseEntity<JsonResponseBase<UserResponseDto>> updateRole(@RequestBody UserUpdateRoleRequestDto dto) {
        return ResponseEntity.ok(userService.updateRoleToUser(dto));
    }

    @GetMapping("/")
    public ResponseEntity<BaseResponseDTO<UserDTO.Response>> getAccount() {
         String user = SecurityUtils.getCurrentUserLogin().orElseThrow();
        return ResponseEntity.ok(BaseResponseDTO.<UserDTO.Response>builder()
                .data(userService.findByUserName(user))
                .build());
    }
}
