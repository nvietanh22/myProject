package vn.lottefinance.pdms_core.service.core;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import vn.lottefinance.pdms_core.config.JsonResponseBase;
import vn.lottefinance.pdms_core.exception.BaseException;
import vn.lottefinance.pdms_core.service.core.dto.user.UserDTO;
import vn.lottefinance.pdms_core.service.core.dto.user.UserRequestDto;
import vn.lottefinance.pdms_core.service.core.dto.user.UserResponseDto;
import vn.lottefinance.pdms_core.service.core.dto.user.UserUpdateRoleRequestDto;
import vn.lottefinance.pdms_core.util.BasePagingResponse;

import java.util.List;

public interface UserService extends UserDetailsService {
    BasePagingResponse<UserDTO.Response> findAll(Pageable pageable);

    UserDTO.Response findById(Long id);

    UserDTO.Response findByUserName(String userName);

    JsonResponseBase<UserResponseDto> create(UserRequestDto request) throws BaseException;

    JsonResponseBase<UserResponseDto> update(Long id, UserRequestDto request);

    void delete(Long id);

    JsonResponseBase<UserResponseDto> updateRoleToUser(UserUpdateRoleRequestDto dto);

}
