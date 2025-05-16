package vn.lottefinance.pdms_core.service.core;

import vn.lottefinance.pdms_core.service.core.dto.RefreshTokenDTO;

import java.util.List;

public interface RefreshTokenService {
    List<RefreshTokenDTO.Response> findAll();
    RefreshTokenDTO.Response findById(Long id);
    RefreshTokenDTO.Response create(RefreshTokenDTO.Request request);
    RefreshTokenDTO.Response update(Long id, RefreshTokenDTO.Request request);
    void delete(Long id);
}
