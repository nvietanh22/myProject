package vn.lottefinance.pdms_core.service.core.dto.user;

import lombok.*;
import vn.lottefinance.pdms_core.domain.Role;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDTO {

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String username;
        private String password;
        private String fullname;
        private String phoneNumber;
        private String identityNumber;
        private String email;
        private String status;
        private String createBy;
        private LocalDateTime createdDate;
        private String modifyBy;
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String username;
        private String password;
        private String fullname;
        private String phoneNumber;
        private String identityNumber;
        private String email;
        private String status;
        private String createBy;
        private LocalDateTime createdDate;
        private String modifyBy;
        private Set<Role> roles = new HashSet<>();
    }
}
