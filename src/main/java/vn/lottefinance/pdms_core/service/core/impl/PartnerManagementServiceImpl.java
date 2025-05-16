package vn.lottefinance.pdms_core.service.core.impl;

import org.springframework.stereotype.Service;
import vn.lottefinance.pdms_core.domain.PartnerFormConfig;
import vn.lottefinance.pdms_core.domain.PartnerManagement;
import vn.lottefinance.pdms_core.repository.PartnerFormConfigRepository;
import vn.lottefinance.pdms_core.repository.PartnerManagementRepository;
import vn.lottefinance.pdms_core.service.core.PartnerFormConfigService;
import vn.lottefinance.pdms_core.service.core.PartnerManagementService;
import vn.lottefinance.pdms_core.service.core.dto.partner.PartnerFormConfigDTO;
import vn.lottefinance.pdms_core.service.core.dto.partner.PartnerManagementDTO;
import vn.lottefinance.pdms_core.service.core.mapper.partner.PartnerFormConfigMapper;
import vn.lottefinance.pdms_core.service.core.mapper.partner.PartnerManagementMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartnerManagementServiceImpl implements PartnerManagementService {

    private final PartnerManagementRepository repository;

    public PartnerManagementServiceImpl(PartnerManagementRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PartnerManagementDTO.Response> findAll() {
        return repository.findAll().stream().map(PartnerManagementMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public PartnerManagementDTO.Response findById(Long id) {
        return repository.findById(id).map(PartnerManagementMapper::toResponseDTO).orElse(null);
    }

    @Override
    public PartnerManagementDTO.Response create(PartnerManagementDTO.Request request) {
        PartnerManagement entity = PartnerManagementMapper.toEntity(request);
        return PartnerManagementMapper.toResponseDTO(repository.save(entity));
    }

    @Override
    public PartnerManagementDTO.Response update(Long id, PartnerManagementDTO.Request request) {
        PartnerManagement entity = PartnerManagementMapper.toEntity(request);
        entity.setId(id);
        return PartnerManagementMapper.toResponseDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
