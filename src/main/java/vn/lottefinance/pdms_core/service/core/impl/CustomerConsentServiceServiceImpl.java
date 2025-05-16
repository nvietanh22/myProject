package vn.lottefinance.pdms_core.service.core.impl;

import vn.lottefinance.pdms_core.domain.CustomerConsentService;
import vn.lottefinance.pdms_core.service.core.dto.customerconsent.CustomerConsentServiceDTO;
import vn.lottefinance.pdms_core.repository.CustomerConsentServiceRepository;
import vn.lottefinance.pdms_core.service.core.CustomerConsentServiceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerConsentServiceServiceImpl implements CustomerConsentServiceService {

    private final CustomerConsentServiceRepository repository;

    public CustomerConsentServiceServiceImpl(CustomerConsentServiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CustomerConsentServiceDTO.Response> findAll() {
        return repository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public CustomerConsentServiceDTO.Response findById(Long id) {
        return repository.findById(id).map(this::toResponse).orElse(null);
    }

    @Override
    public CustomerConsentServiceDTO.Response create(CustomerConsentServiceDTO.Request request) {
        CustomerConsentService entity = toEntity(request);
        return toResponse(repository.save(entity));
    }

    @Override
    public CustomerConsentServiceDTO.Response update(Long id, CustomerConsentServiceDTO.Request request) {
        CustomerConsentService entity = toEntity(request);
        entity.setId(id);
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private CustomerConsentServiceDTO.Response toResponse(CustomerConsentService entity) {
        return CustomerConsentServiceDTO.Response.builder()
            .id(entity.getId())
            .customerConsentId(entity.getCustomerConsentId())
            .serviceId(entity.getServiceId())
            .createdDate(entity.getCreatedDate())
            .createdBy(entity.getCreatedBy())
            .build();
    }

    private CustomerConsentService toEntity(CustomerConsentServiceDTO.Request request) {
        return CustomerConsentService.builder()
            .customerConsentId(request.getCustomerConsentId())
            .serviceId(request.getServiceId())
            .createdDate(request.getCreatedDate())
            .createdBy(request.getCreatedBy())
            .build();
    }
}
