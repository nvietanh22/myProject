package vn.lottefinance.pdms_core.service.core.dto.permission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.lottefinance.pdms_core.domain.Permission;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionResponseDto {
    private Long id;
    private String name;
    private String action;
    private String createAt;
    private String createBy;


    public static PermissionResponseDto from(Permission permission) {
        return PermissionResponseDto.builder()
                .id(permission.getId())
                .name(permission.toString())
                .build();
    }

}
