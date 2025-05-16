package vn.lottefinance.pdms_core.service.core.impl;

import vn.lottefinance.pdms_core.domain.RefreshToken;
import vn.lottefinance.pdms_core.service.core.dto.RefreshTokenDTO;
import vn.lottefinance.pdms_core.repository.RefreshTokenRepository;
import vn.lottefinance.pdms_core.service.core.RefreshTokenService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository repository;

    public RefreshTokenServiceImpl(RefreshTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<RefreshTokenDTO.Response> findAll() {
        return repository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public RefreshTokenDTO.Response findById(Long id) {
        return repository.findById(id).map(this::toResponse).orElse(null);
    }

    @Override
    public RefreshTokenDTO.Response create(RefreshTokenDTO.Request request) {
        RefreshToken entity = toEntity(request);
        return toResponse(repository.save(entity));
    }

    @Override
    public RefreshTokenDTO.Response update(Long id, RefreshTokenDTO.Request request) {
        RefreshToken entity = toEntity(request);
        entity.setId(id);
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private RefreshTokenDTO.Response toResponse(RefreshToken entity) {
        return RefreshTokenDTO.Response.builder()
            .id(entity.getId())
            .refreshToken(entity.getRefreshToken())
            .userID(entity.getUserID())
            .createdDate(entity.getCreatedDate())
            .endDate(entity.getEndDate())
            .build();
    }

    private RefreshToken toEntity(RefreshTokenDTO.Request request) {
        return RefreshToken.builder()
            .refreshToken(request.getRefreshToken())
            .userID(request.getUserID())
            .createdDate(request.getCreatedDate())
            .endDate(request.getEndDate())
            .build();
    }
}
