package vn.lottefinance.pdms_core.service.core.impl;

import vn.lottefinance.pdms_core.domain.SystemConfig;
import vn.lottefinance.pdms_core.service.core.dto.SystemConfigDTO;
import vn.lottefinance.pdms_core.repository.SystemConfigRepository;
import vn.lottefinance.pdms_core.service.core.SystemConfigService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SystemConfigServiceImpl implements SystemConfigService {

    private final SystemConfigRepository repository;

    public SystemConfigServiceImpl(SystemConfigRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<SystemConfigDTO.Response> findAll() {
        return repository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public SystemConfigDTO.Response findById(Long id) {
        return repository.findById(id).map(this::toResponse).orElse(null);
    }

    @Override
    public SystemConfigDTO.Response create(SystemConfigDTO.Request request) {
        SystemConfig entity = toEntity(request);
        return toResponse(repository.save(entity));
    }

    @Override
    public SystemConfigDTO.Response update(Long id, SystemConfigDTO.Request request) {
        SystemConfig entity = toEntity(request);
        entity.setId(id);
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private SystemConfigDTO.Response toResponse(SystemConfig entity) {
        return SystemConfigDTO.Response.builder()
            .id(entity.getId())
            .code(entity.getCode())
            .name(entity.getName())
            .dataType(entity.getDataType())
            .strVal(entity.getStrVal())
            .intVal(entity.getIntVal())
            .doubleVal(entity.getDoubleVal())
            .createdBy(entity.getCreatedBy())
            .createdDate(entity.getCreatedDate())
            .status(entity.getStatus())
            .build();
    }

    private SystemConfig toEntity(SystemConfigDTO.Request request) {
        return SystemConfig.builder()
            .code(request.getCode())
            .name(request.getName())
            .dataType(request.getDataType())
            .strVal(request.getStrVal())
            .intVal(request.getIntVal())
            .doubleVal(request.getDoubleVal())
            .createdBy(request.getCreatedBy())
            .createdDate(request.getCreatedDate())
            .status(request.getStatus())
            .build();
    }
}
