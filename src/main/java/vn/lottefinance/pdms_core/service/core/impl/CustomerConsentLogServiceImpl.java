package vn.lottefinance.pdms_core.service.core.impl;

import org.springframework.stereotype.Service;
import vn.lottefinance.pdms_core.domain.CustomerConsent;
import vn.lottefinance.pdms_core.domain.CustomerConsentLog;
import vn.lottefinance.pdms_core.repository.CustomerConsentLogRepository;
import vn.lottefinance.pdms_core.repository.CustomerConsentRepository;
import vn.lottefinance.pdms_core.service.core.CustomerConsentLogService;
import vn.lottefinance.pdms_core.service.core.CustomerConsentService;
import vn.lottefinance.pdms_core.service.core.dto.customerconsent.CustomerConsentDTO;
import vn.lottefinance.pdms_core.service.core.dto.customerconsent.CustomerConsentLogDTO;
import vn.lottefinance.pdms_core.service.core.mapper.customerconsent.CustomerConsentLogMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerConsentLogServiceImpl implements CustomerConsentLogService {

    private final CustomerConsentLogRepository repository;

    public CustomerConsentLogServiceImpl(CustomerConsentLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CustomerConsentLogDTO.Response> findAll() {
        return repository.findAll().stream().map(CustomerConsentLogMapper::toResponsetDTO).collect(Collectors.toList());
    }

    @Override
    public CustomerConsentLogDTO.Response findById(Long id) {
        return repository.findById(id).map(CustomerConsentLogMapper::toResponsetDTO).orElse(null);
    }

    @Override
    public CustomerConsentLogDTO.Response create(CustomerConsentLogDTO.Request request) {
        CustomerConsentLog entity = CustomerConsentLogMapper.toEntity(request);
        return CustomerConsentLogMapper.toResponsetDTO(repository.save(entity));
    }

    @Override
    public CustomerConsentLogDTO.Response update(Long id, CustomerConsentLogDTO.Request request) {
        CustomerConsentLog entity = CustomerConsentLogMapper.toEntity(request);
        entity.setId(id);
        return CustomerConsentLogMapper.toResponsetDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
