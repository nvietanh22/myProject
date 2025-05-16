package vn.lottefinance.pdms_core.service.core.impl;

import org.springframework.stereotype.Service;
import vn.lottefinance.pdms_core.domain.Token;
import vn.lottefinance.pdms_core.repository.TokenRepository;
import vn.lottefinance.pdms_core.service.core.TokenService;
import vn.lottefinance.pdms_core.service.core.dto.TokenDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository repository;

    public TokenServiceImpl(TokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TokenDTO.Response> findAll() {
        return repository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public TokenDTO.Response findById(Long id) {
        return repository.findById(id).map(this::toResponse).orElse(null);
    }

    @Override
    public TokenDTO.Response create(TokenDTO.Request request) {
        Token entity = toEntity(request);
        return toResponse(repository.save(entity));
    }

    @Override
    public TokenDTO.Response update(Long id, TokenDTO.Request request) {
        Token entity = toEntity(request);
        entity.setId(id);
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private TokenDTO.Response toResponse(Token entity) {
        return TokenDTO.Response.builder()
                .id(entity.getId())
                .token(entity.getToken())
                .userID(entity.getUserID())
                .Status(entity.getStatus())
                .createdDate(entity.getCreatedDate())
                .endDate(entity.getEndDate())
                .build();
    }

    private Token toEntity(TokenDTO.Request request) {
        return Token.builder()
                .token(request.getToken())
                .userID(request.getUserID())
                .Status(request.getStatus())
                .createdDate(request.getCreatedDate())
                .endDate(request.getEndDate())
                .build();
    }
}
