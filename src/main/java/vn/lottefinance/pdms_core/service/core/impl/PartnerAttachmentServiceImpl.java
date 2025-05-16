package vn.lottefinance.pdms_core.service.core.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vn.lottefinance.pdms_core.domain.PartnerAttachment;
import vn.lottefinance.pdms_core.domain.PartnerManagement;
import vn.lottefinance.pdms_core.exception.RestException;
import vn.lottefinance.pdms_core.repository.PartnerAttachmentRepository;
import vn.lottefinance.pdms_core.repository.PartnerManagementRepository;
import vn.lottefinance.pdms_core.service.core.PartnerAttachmentService;
import vn.lottefinance.pdms_core.service.core.PartnerManagementService;
import vn.lottefinance.pdms_core.service.core.dto.partner.PartnerAttachmentDTO;
import vn.lottefinance.pdms_core.service.core.dto.partner.PartnerManagementDTO;
import vn.lottefinance.pdms_core.service.core.mapper.partner.PartnerAttachmentMapper;
import vn.lottefinance.pdms_core.service.core.mapper.partner.PartnerManagementMapper;
import vn.lottefinance.pdms_core.service.external.MinioService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PartnerAttachmentServiceImpl implements PartnerAttachmentService {

    private final PartnerAttachmentRepository repository;
    private final PartnerManagementRepository managementRepository;
    private final MinioService minioService;

    @Override
    public List<PartnerAttachmentDTO.Response> findAll() {
        return repository.findAll().stream().map(PartnerAttachmentMapper::toRespone).collect(Collectors.toList());
    }

    @Override
    public PartnerAttachmentDTO.Response findById(Long id) {
        return repository.findById(id).map(PartnerAttachmentMapper::toRespone).orElse(null);
    }

    @Override
    @Transactional
    public PartnerAttachmentDTO.Response create(PartnerAttachmentDTO.Request request, Long partnerId, MultipartFile file) {
        PartnerManagement partnerManagement = managementRepository.findById(partnerId).orElse(null);
        if (partnerManagement == null) {
            throw new RestException(HttpStatus.NOT_FOUND, "Partner not found");
        }

        String fileUrl = String.format("%s/%s", partnerManagement.getPartnerCode(), request.getFileName());
        request.setFileUrl(fileUrl);
        PartnerAttachment entity = PartnerAttachmentMapper.toEntityFromReq(request);
        entity.setPartnerManagement(partnerManagement);
        minioService.uploadFile(file, fileUrl);
        return PartnerAttachmentMapper.toRespone(repository.save(entity));
    }

    @Override
    public PartnerAttachmentDTO.Response update(Long id, PartnerAttachmentDTO.Request request) {
        PartnerAttachment entity = PartnerAttachmentMapper.toEntityFromReq(request);
        entity.setId(id);
        return PartnerAttachmentMapper.toRespone(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
