package vn.lottefinance.pdms_core.service.core.dto.permission;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String action;
    private LocalDateTime createAt;
    private String createBy;
}
