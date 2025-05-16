package vn.lottefinance.pdms_core.service.core.dto.user;

import lombok.Data;

import java.util.List;

@Data
public class UserUpdateRoleRequestDto {
    private Long userId;
    private List<Long> roleIdList;
}
