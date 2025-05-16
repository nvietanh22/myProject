package vn.lottefinance.pdms_core.service.core.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.lottefinance.pdms_core.config.JsonResponseBase;
import vn.lottefinance.pdms_core.domain.Permission;
import vn.lottefinance.pdms_core.enums.SystemCodeEnum;
import vn.lottefinance.pdms_core.repository.PermissionRepository;
import vn.lottefinance.pdms_core.service.core.PermissionService;
import vn.lottefinance.pdms_core.service.core.dto.permission.PermissionDTO;
import vn.lottefinance.pdms_core.service.core.dto.permission.PermissionRequestDto;
import vn.lottefinance.pdms_core.service.core.dto.permission.PermissionResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<PermissionDTO.Response> findAll() {
        return permissionRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public PermissionDTO.Response findById(Long id) {
        return permissionRepository.findById(id).map(this::toResponse).orElse(null);
    }

    @Override
    public JsonResponseBase<PermissionResponseDto> create(PermissionRequestDto request) {
        JsonResponseBase<PermissionResponseDto> resp = new JsonResponseBase<>();
        try {
            Permission permission = permissionRepository.findPermissionByName(request.getName());
            if (permission != null) {
                resp.setStatus(SystemCodeEnum.DUPLICATE.getCode());
                resp.setMessage(SystemCodeEnum.DUPLICATE.getMessage() + " " + request.getName());
                return resp;
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            permission = Permission.builder()
                    .name(request.getName())
                    .action(request.getAction())
                    .createAt(LocalDateTime.now())
                    .createBy(username)
                    .build();
            permissionRepository.save(permission);
            resp.setData(PermissionResponseDto.from(permission));
            resp.setStatus(SystemCodeEnum.SUCCESS.getCode());
            resp.setMessage(SystemCodeEnum.SUCCESS.getMessage());
            return resp;
        } catch (Exception e) {
            resp.setStatus(SystemCodeEnum.INTERNAL_SERVER_ERROR.getCode());
            resp.setMessage(e.getMessage());
            return resp;
        }
    }

//    @Override
//    public PermissionDTO.Response update(Long id, PermissionDTO.Request request) {
//        Permission entity = toEntity(request);
//        entity.setId(id);
//        return toResponse(permissionRepository.save(entity));
//    }
//
//    @Override
//    public void delete(Long id) {
//        permissionRepository.deleteById(id);
//    }

    private PermissionDTO.Response toResponse(Permission entity) {
        return PermissionDTO.Response.builder()
                .id(entity.getId())
                .name(entity.getName())
                .action(entity.getAction())
                .createAt(entity.getCreateAt())
                .createBy(entity.getCreateBy())
                .build();
    }

    private Permission toEntity(PermissionDTO.Request request) {
        return Permission.builder()
                .name(request.getName())
                .action(request.getAction())
                .createAt(request.getCreateAt())
                .createBy(request.getCreateBy())
                .build();
    }
}
