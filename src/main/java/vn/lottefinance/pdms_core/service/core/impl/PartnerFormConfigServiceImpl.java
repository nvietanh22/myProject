package vn.lottefinance.pdms_core.service.core.impl;

import org.springframework.stereotype.Service;
import vn.lottefinance.pdms_core.domain.CampainNotiDetail;
import vn.lottefinance.pdms_core.domain.PartnerFormConfig;
import vn.lottefinance.pdms_core.repository.CampainNotiDetailRepository;
import vn.lottefinance.pdms_core.repository.PartnerFormConfigRepository;
import vn.lottefinance.pdms_core.service.core.CampainNotiDetailService;
import vn.lottefinance.pdms_core.service.core.PartnerFormConfigService;
import vn.lottefinance.pdms_core.service.core.dto.campain.CampainNotiDetailDTO;
import vn.lottefinance.pdms_core.service.core.dto.partner.PartnerFormConfigDTO;
import vn.lottefinance.pdms_core.service.core.mapper.partner.PartnerFormConfigMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartnerFormConfigServiceImpl implements PartnerFormConfigService {

    private final PartnerFormConfigRepository repository;

    public PartnerFormConfigServiceImpl(PartnerFormConfigRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PartnerFormConfigDTO.Response> findAll() {
        return repository.findAll().stream().map(PartnerFormConfigMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public PartnerFormConfigDTO.Response findById(Long id) {
        return repository.findById(id).map(PartnerFormConfigMapper::toResponseDTO).orElse(null);
    }

    @Override
    public PartnerFormConfigDTO.Response create(PartnerFormConfigDTO.Request request) {
        PartnerFormConfig entity = PartnerFormConfigMapper.toEntity(request);
        return PartnerFormConfigMapper.toResponseDTO(repository.save(entity));
    }

    @Override
    public PartnerFormConfigDTO.Response update(Long id, PartnerFormConfigDTO.Request request) {
        PartnerFormConfig entity = PartnerFormConfigMapper.toEntity(request);
        entity.setId(id);
        return PartnerFormConfigMapper.toResponseDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
