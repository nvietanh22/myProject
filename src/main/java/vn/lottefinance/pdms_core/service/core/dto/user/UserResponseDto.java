package vn.lottefinance.pdms_core.service.core.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.lottefinance.pdms_core.domain.User;
import vn.lottefinance.pdms_core.service.core.dto.role.RoleResponseDto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String username;
    private LocalDateTime createdDate;
    private String fullName;
    private String email;
    private Collection<RoleResponseDto> roles;

    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .createdDate(user.getCreatedDate())
                .fullName(user.getFullname())
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(RoleResponseDto::from).collect(Collectors.toList()))
                .build();
    }
}
