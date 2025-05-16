package vn.lottefinance.pdms_core.service.core;

import vn.lottefinance.pdms_core.config.JsonResponseBase;
import vn.lottefinance.pdms_core.service.core.dto.permission.PermissionDTO;
import vn.lottefinance.pdms_core.service.core.dto.permission.PermissionRequestDto;
import vn.lottefinance.pdms_core.service.core.dto.permission.PermissionResponseDto;

import java.util.List;

public interface PermissionService {
    List<PermissionDTO.Response> findAll();

    PermissionDTO.Response findById(Long id);

    JsonResponseBase<PermissionResponseDto> create(PermissionRequestDto request);

//    PermissionDTO.Response update(Long id, PermissionDTO.Request request);
//
//    void delete(Long id);
}
