package vn.lottefinance.pdms_core.service.core.impl;

import vn.lottefinance.pdms_core.domain.CampainNotiDetail;
import vn.lottefinance.pdms_core.service.core.dto.campain.CampainNotiDetailDTO;
import vn.lottefinance.pdms_core.repository.CampainNotiDetailRepository;
import vn.lottefinance.pdms_core.service.core.CampainNotiDetailService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampainNotiDetailServiceImpl implements CampainNotiDetailService {

    private final CampainNotiDetailRepository repository;

    public CampainNotiDetailServiceImpl(CampainNotiDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CampainNotiDetailDTO.Response> findAll() {
        return repository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public CampainNotiDetailDTO.Response findById(Long id) {
        return repository.findById(id).map(this::toResponse).orElse(null);
    }

    @Override
    public CampainNotiDetailDTO.Response create(CampainNotiDetailDTO.Request request) {
        CampainNotiDetail entity = toEntity(request);
        return toResponse(repository.save(entity));
    }

    @Override
    public CampainNotiDetailDTO.Response update(Long id, CampainNotiDetailDTO.Request request) {
        CampainNotiDetail entity = toEntity(request);
        entity.setId(id);
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private CampainNotiDetailDTO.Response toResponse(CampainNotiDetail entity) {
        return CampainNotiDetailDTO.Response.builder()
            .id(entity.getId())
            .campainNotiId(entity.getCampainNoti().getId())
            .cifNumber(entity.getCifNumber())
            .idCardNumber(entity.getIdCardNumber())
            .customerName(entity.getCustomerName())
            .phoneNumber(entity.getPhoneNumber())
            .email(entity.getEmail())
            .notiChannel(entity.getNotiChannel())
            .creatDate(entity.getCreatedDate())
            .status(entity.getStatus())
            .build();
    }

    private CampainNotiDetail toEntity(CampainNotiDetailDTO.Request request) {
        return CampainNotiDetail.builder()
            .cifNumber(request.getCifNumber())
            .idCardNumber(request.getIdCardNumber())
            .customerName(request.getCustomerName())
            .phoneNumber(request.getPhoneNumber())
            .email(request.getEmail())
            .notiChannel(request.getNotiChannel())
            .createdDate(request.getCreatDate())
            .status(request.getStatus())
            .build();
    }
}
