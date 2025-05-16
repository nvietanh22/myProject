package vn.lottefinance.pdms_core.service.core;

import vn.lottefinance.pdms_core.config.JsonResponseBase;
import vn.lottefinance.pdms_core.config.SearchResult;
import vn.lottefinance.pdms_core.service.core.dto.role.*;

import java.util.List;

public interface RoleService {
    List<RoleDTO.Response> findAll();
    RoleDTO.Response findById(Long id);
    JsonResponseBase<RoleResponseDto> create(RoleRequestDto request);
    JsonResponseBase<RoleResponseDto> updatePermissionToRole(RoleUpdatePermissionRequestDto requestDto);
//    RoleDTO.Response update(Long id, RoleDTO.Request request);
    void delete(Long id);
    SearchResult<List<RoleResponseDto>> listAllActiveRole(RoleSearchRequestDto roleSearchRequestDto);

    List<String> getPermissionByRoles(List<String> roles);


}
