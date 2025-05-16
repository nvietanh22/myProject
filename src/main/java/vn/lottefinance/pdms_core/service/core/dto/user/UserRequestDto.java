package vn.lottefinance.pdms_core.service.core.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String fullName;
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10,11}$", message = "Phone number must be 10 or 11 digits")
    private String phoneNumber;
    @NotBlank(message = "Identity number is required")
    @Pattern(regexp = "^\\d{12}$", message = "Identity number must be 12 digits")
    private String identityNumber;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String status;
    @NotBlank
    private String createBy;
    @NotBlank
    private LocalDateTime createdDate;
    @NotBlank
    private String modifyBy;

}
