package vn.lottefinance.pdms_core.service.core.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import vn.lottefinance.pdms_core.config.JsonResponseBase;
import vn.lottefinance.pdms_core.domain.Permission;
import vn.lottefinance.pdms_core.domain.Role;
import vn.lottefinance.pdms_core.domain.User;
import vn.lottefinance.pdms_core.enums.SystemCodeEnum;
import vn.lottefinance.pdms_core.enums.UserStatus;
import vn.lottefinance.pdms_core.exception.BaseException;
import vn.lottefinance.pdms_core.repository.RoleRepository;
import vn.lottefinance.pdms_core.repository.UserRepository;
import vn.lottefinance.pdms_core.service.core.UserService;
import vn.lottefinance.pdms_core.service.core.dto.user.UserDTO;
import vn.lottefinance.pdms_core.service.core.dto.user.UserRequestDto;
import vn.lottefinance.pdms_core.service.core.dto.user.UserResponseDto;
import vn.lottefinance.pdms_core.service.core.dto.user.UserUpdateRoleRequestDto;
import vn.lottefinance.pdms_core.util.BasePagingResponse;
import vn.lottefinance.pdms_core.util.PagingUtil;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    @Lazy
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public BasePagingResponse<UserDTO.Response> findAll(Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );
        Page<User> page = userRepository.findAll(sortedPageable);
        Page<UserDTO.Response> dtoPage = page.map(this::toResponse);
        return PagingUtil.fromPage(dtoPage);
    }

    @Override
    public UserDTO.Response findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
        return toResponse(user);
    }


    @Override
    public UserDTO.Response findByUserName(String userName) {
        User user = userRepository.findFirstByUsername(userName);
        return toResponse(user);
    }

    @Override
    public JsonResponseBase<UserResponseDto> create(UserRequestDto request) throws BaseException {
        JsonResponseBase<UserResponseDto> resp = new JsonResponseBase<>();
        try {
            User user = userRepository.findFirstByUsername(request.getUsername());
            if (user != null) {
                resp.setStatus(SystemCodeEnum.DUPLICATE.getCode());
                resp.setMessage(SystemCodeEnum.DUPLICATE.getMessage() + " " + request.getUsername());
                return resp;
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            String password = bCryptPasswordEncoder.encode(request.getPassword());
            User entity = User.builder()
                    .username(request.getUsername())
                    .password(password)
                    .fullname(request.getFullName())
                    .phoneNumber(request.getPhoneNumber())
                    .identityNumber(request.getIdentityNumber())
                    .email(request.getEmail())
                    .status(UserStatus.ACTIVE.getValue())
                    .createBy(username)
                    .createdDate(request.getCreatedDate())
                    .modifyBy(username)
                    .roles(new HashSet<>())
                    .build();
            userRepository.save(entity);
            resp.setData(UserResponseDto.from(entity));
            resp.setStatus(SystemCodeEnum.SUCCESS.getCode());
            resp.setMessage(SystemCodeEnum.SUCCESS.getMessage());
            return resp;
        } catch (Exception e) {
            resp.setStatus(SystemCodeEnum.INTERNAL_SERVER_ERROR.getCode());
            resp.setMessage(e.getMessage());
            return resp;
        }
    }


    @Override
    public JsonResponseBase<UserResponseDto> update(Long id, UserRequestDto request) {
        JsonResponseBase<UserResponseDto> resp = new JsonResponseBase<>();
        try {
            User user = userRepository.findFirstByUsername(request.getUsername());
            if (user == null) {
                resp.setStatus(SystemCodeEnum.NOT_FOUND.getCode());
                resp.setMessage(SystemCodeEnum.NOT_FOUND.getMessage() + " " + request.getUsername());
                return resp;
            }
            user = User.builder()
                    .username(request.getUsername())
                    .password(request.getPassword())
                    .fullname(request.getFullName())
                    .phoneNumber(request.getPhoneNumber())
                    .identityNumber(request.getIdentityNumber())
                    .email(request.getEmail())
                    .status(request.getStatus())
                    .createBy(request.getCreateBy())
                    .createdDate(request.getCreatedDate())
                    .modifyBy(request.getModifyBy())
                    .roles(new HashSet<>())
                    .build();
            user.setId(id);
            userRepository.save(user);
            resp.setData(UserResponseDto.from(user));
            resp.setStatus(SystemCodeEnum.SUCCESS.getCode());
            resp.setMessage(SystemCodeEnum.SUCCESS.getMessage());
            return resp;
        } catch (Exception e) {
            resp.setStatus(SystemCodeEnum.INTERNAL_SERVER_ERROR.getCode());
            resp.setMessage(e.getMessage());
            return resp;
        }
    }


    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new org.springframework.security.core.userdetails.User(
                user.get().getUsername(),
                user.get().getUsername(),
                true,
                true,
                true,
                true,
                getAuthorities(user.get().getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toSet());
    }

    private List<String> getPermissions(Collection<Role> roles) {
        List<String> res = new ArrayList<>();
        for (Role role : roles) {
            res.addAll(role.getPermissions().stream()
                    .map(Permission::toString)
                    .collect(Collectors.toList()));
        }
        return res;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> permissions) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        return authorities;
    }

    @Override
    public JsonResponseBase<UserResponseDto> updateRoleToUser(UserUpdateRoleRequestDto dto) {
        JsonResponseBase<UserResponseDto> resp = new JsonResponseBase<>();
        try {
            User user = userRepository.findUserById(dto.getUserId());
            if (user == null) {
                resp.setStatus(SystemCodeEnum.NOT_FOUND.getCode());
                resp.setMessage(SystemCodeEnum.NOT_FOUND.getMessage());
                return resp;
            }

            List<Role> roles = new ArrayList<>();
            Role role;
            for (Long roleId : dto.getRoleIdList()) {
                role = roleRepository.findRoleById(roleId);
                if (role == null) {
                    resp.setStatus(SystemCodeEnum.NOT_FOUND.getCode());
                    resp.setMessage("Role " + roleId.toString() + " not found");
                    return resp;
                }
                roles.add(role);
            }
            user.setRoles((Set<Role>) roles);
            userRepository.save(user);
            resp.setData(UserResponseDto.from(user));
            resp.setStatus(SystemCodeEnum.SUCCESS.getCode());
            resp.setMessage(SystemCodeEnum.SUCCESS.getMessage());
            return resp;
        } catch (Exception e) {
            log.error(e.getMessage());
            resp.setStatus(SystemCodeEnum.INTERNAL_SERVER_ERROR.getCode());
            resp.setMessage(e.getMessage());
            return resp;
        }
    }

    private UserDTO.Response toResponse(User entity) {
        return UserDTO.Response.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .fullname(entity.getFullname())
                .phoneNumber(entity.getPhoneNumber())
                .identityNumber(entity.getIdentityNumber())
                .email(entity.getEmail())
                .status(entity.getStatus())
                .createBy(entity.getCreateBy())
                .createdDate(entity.getCreatedDate())
                .modifyBy(entity.getModifyBy())
                .build();
    }

}
