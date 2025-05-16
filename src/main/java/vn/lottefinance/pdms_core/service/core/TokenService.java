package vn.lottefinance.pdms_core.service.core;

import vn.lottefinance.pdms_core.service.core.dto.TokenDTO;

import java.util.List;

public interface TokenService {
    List<TokenDTO.Response> findAll();
    TokenDTO.Response findById(Long id);
    TokenDTO.Response create(TokenDTO.Request request);
    TokenDTO.Response update(Long id, TokenDTO.Request request);
    void delete(Long id);
}
