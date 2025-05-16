package vn.lottefinance.pdms_core.service.core.dto.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.lottefinance.pdms_core.domain.Role;
import vn.lottefinance.pdms_core.service.core.dto.permission.PermissionResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponseDto {
    private Long id;
    private String name;
    private List<PermissionResponseDto> permissions;

    public static RoleResponseDto from(Role role) {
        return RoleResponseDto.builder()
                .id(role.getId())
                .name(role.getRoleName())
                .permissions(Optional.ofNullable(role.getPermissions())
                        .orElse(new ArrayList<>())
                        .stream()
                        .map(PermissionResponseDto::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
