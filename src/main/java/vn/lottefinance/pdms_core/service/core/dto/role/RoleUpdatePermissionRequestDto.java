package vn.lottefinance.pdms_core.service.core.dto.role;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class RoleUpdatePermissionRequestDto {
    @NotBlank
    private Long roleId;
    @NotBlank
    private List<Long> permissionIdList;
}
