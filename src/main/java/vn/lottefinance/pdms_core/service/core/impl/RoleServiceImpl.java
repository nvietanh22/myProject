package vn.lottefinance.pdms_core.service.core.impl;

import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.lottefinance.pdms_core.config.JsonResponseBase;
import vn.lottefinance.pdms_core.config.SearchResult;
import vn.lottefinance.pdms_core.domain.Permission;
import vn.lottefinance.pdms_core.domain.Role;
import vn.lottefinance.pdms_core.enums.SystemCodeEnum;
import vn.lottefinance.pdms_core.repository.PermissionRepository;
import vn.lottefinance.pdms_core.repository.RoleRepository;
import vn.lottefinance.pdms_core.service.core.RoleService;
import vn.lottefinance.pdms_core.service.core.dto.role.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<RoleDTO.Response> findAll() {
        return roleRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public RoleDTO.Response findById(Long id) {
        return roleRepository.findById(id).map(this::toResponse).orElse(null);
    }

    @Override
    public JsonResponseBase<RoleResponseDto> create(RoleRequestDto request) {
        JsonResponseBase<RoleResponseDto> resp = new JsonResponseBase<>();
        try {
            Role role = roleRepository.findByRoleName(request.getRoleName());
            if (role != null) {
                resp.setStatus(SystemCodeEnum.DUPLICATE.getCode());
                resp.setMessage(SystemCodeEnum.DUPLICATE.getMessage() + " " + request.getRoleName());
                return resp;
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            role = Role.builder()
                    .roleName(request.getRoleName())
                    .build();
            roleRepository.save(role);
            resp.setData(RoleResponseDto.from(role));
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
    public JsonResponseBase<RoleResponseDto> updatePermissionToRole(RoleUpdatePermissionRequestDto requestDto) {
        JsonResponseBase<RoleResponseDto> resp = new JsonResponseBase<>();
        try {
            Role role = roleRepository.findRoleById(requestDto.getRoleId());
            if (role == null) {
                resp.setStatus(SystemCodeEnum.NOT_FOUND.getCode());
                resp.setMessage(SystemCodeEnum.NOT_FOUND.getMessage());
                return resp;
            }
            List<Permission> permissions = new ArrayList<>();
            Permission permission;
            for (Long permissionId : requestDto.getPermissionIdList()) {
                permission = permissionRepository.findPermissionById(permissionId);
                if (permission == null) {
                    resp.setStatus(SystemCodeEnum.NOT_FOUND.getCode());
                    resp.setMessage("Permission " + permissionId.toString() + " not found");
                    return resp;
                }
                permissions.add(permission);
            }
            role.setPermissions(permissions);
            roleRepository.save(role);
            resp.setData(RoleResponseDto.from(role));
            resp.setStatus(SystemCodeEnum.SUCCESS.getCode());
            resp.setMessage(SystemCodeEnum.SUCCESS.getMessage());
            return resp;
        } catch (Exception e) {

            resp.setStatus(SystemCodeEnum.INTERNAL_SERVER_ERROR.getCode());
            resp.setMessage(e.getMessage());
            return resp;
        }
    }
//
//    @Override
//    public RoleDTO.Response update(Long id, RoleDTO.Request request) {
//        Role entity = toEntity(request);
//        entity.setId(id);
//        return toResponse(roleRepository.save(entity));
//    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public SearchResult<List<RoleResponseDto>> listAllActiveRole(RoleSearchRequestDto roleSearchRequestDto) {
        SearchResult<List<RoleResponseDto>> searchResult = new SearchResult<>();
        try {
            Pageable pageable = PageRequest.of(roleSearchRequestDto.getPage(), roleSearchRequestDto.getRowsPerPage());
            Specification<Role> specification = (root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();
                for (Field field : roleSearchRequestDto.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    try {
                        Object value = field.get(roleSearchRequestDto);
                        if (value != null && !value.toString().trim().isEmpty()) {
                            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(field.getName())), "%" + value.toString().trim().toUpperCase() + "%"));
                        }
                    } catch (IllegalAccessException e) {

                    }
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            };
            Page<Role> rolePage = roleRepository.findAll(specification, pageable);
            searchResult.setData(rolePage.getContent().stream()
                    .map(RoleResponseDto::from)
                    .collect(Collectors.toList()));
            searchResult.setTotalElements(rolePage.getTotalElements());
            searchResult.setTotalPages(rolePage.getTotalPages());
            searchResult.setStatus(SystemCodeEnum.SUCCESS.getCode());
            searchResult.setMessage(SystemCodeEnum.SUCCESS.getMessage());
            return searchResult;
        } catch (Exception e) {
            searchResult.setStatus(SystemCodeEnum.INTERNAL_SERVER_ERROR.getCode());
            searchResult.setMessage(e.getMessage());
            return searchResult;
        }
    }

    @Override
    public List<String> getPermissionByRoles(List<String> roles) {
        List<Permission> permissions = new ArrayList<>();
        List<String> result = new ArrayList<>();
        List<Role> roleList = roleRepository.findRolesByRoleNameIn(roles);
        if (roleList != null && roleList.size() > 0) {
            for (Role role : roleList) {
                List<Permission> permissionsList = role.getPermissions().stream().toList();
                if (permissionsList != null && permissionsList.size() > 0) {
                    permissions.addAll(permissionsList);
                }
            }
        }

        if (permissions != null && permissions.size() > 0) {
            for (Permission permission : permissions) {
                result.add(String.format("%s-%s", permission.getName(), permission.getAction()));
            }
        }
        return result;
    }

    private RoleDTO.Response toResponse(Role entity) {
        return RoleDTO.Response.builder()
                .id(entity.getId())
                .roleName(entity.getRoleName())
                .build();
    }

    private Role toEntity(RoleDTO.Request request) {
        return Role.builder()
                .roleName(request.getRoleName())
                .build();
    }
}
