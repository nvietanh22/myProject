package vn.lottefinance.pdms_core.service.core.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateRequestDto {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
}
