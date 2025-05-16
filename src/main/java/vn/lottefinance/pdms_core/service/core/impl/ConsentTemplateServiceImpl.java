package vn.lottefinance.pdms_core.service.core.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.lottefinance.pdms_core.config.JsonResponseBase;
import vn.lottefinance.pdms_core.domain.ConsentTemplate;
import vn.lottefinance.pdms_core.enums.SystemCodeEnum;
import vn.lottefinance.pdms_core.repository.ConsentTemplateRepository;
import vn.lottefinance.pdms_core.service.core.ConsentTemplateService;
import vn.lottefinance.pdms_core.service.core.dto.ConsentTemplateDTO;
import vn.lottefinance.pdms_core.service.core.dto.ConsentTemplateRequestDTO;
import vn.lottefinance.pdms_core.service.core.dto.ConsentTemplateResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsentTemplateServiceImpl implements ConsentTemplateService {

    private final ConsentTemplateRepository templateRepository;

    public ConsentTemplateServiceImpl(ConsentTemplateRepository repository) {
        this.templateRepository = repository;
    }

    @Override
    public List<ConsentTemplateDTO.Response> findAll() {
        return templateRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public ConsentTemplateDTO.Response findById(Long id) {
        return templateRepository.findById(id).map(this::toResponse).orElse(null);
    }

    @Override
    public JsonResponseBase<ConsentTemplateResponseDTO> create(ConsentTemplateRequestDTO request) {
        JsonResponseBase<ConsentTemplateResponseDTO> resp = new JsonResponseBase<>();
        try {
            ConsentTemplate consentTemplate = templateRepository.findFirstByTempName(request.getTempName());
            if (consentTemplate != null) {
                resp.setStatus(SystemCodeEnum.DUPLICATE.getCode());
                resp.setMessage(SystemCodeEnum.DUPLICATE.getMessage() + " " + request.getTempName());
                return resp;
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            ConsentTemplate template = ConsentTemplate.builder()
                    .tempName(request.getTempName())
                    .content(request.getContent())
                    .level1(request.getLevel1())
                    .level2(request.getLevel2())
                    .channel(request.getChannel())
                    .createdDate(request.getCreatedDate())
                    .createdBy(username)
                    .modifyDate(request.getModifyDate())
                    .status(request.getStatus())
                    .build();
            templateRepository.save(template);
            resp.setData(ConsentTemplateResponseDTO.from(template));
            resp.setStatus(SystemCodeEnum.SUCCESS.getCode());
            resp.setMessage(SystemCodeEnum.SUCCESS.getMessage());
            return resp;
        } catch (Exception e) {
            resp.setStatus(SystemCodeEnum.INTERNAL_SERVER_ERROR.getCode());
            resp.setMessage(e.getMessage());
            return resp;
        }

    }

    @Override
    public JsonResponseBase<ConsentTemplateResponseDTO> update(Long id, ConsentTemplateRequestDTO request) {
        JsonResponseBase<ConsentTemplateResponseDTO> resp = new JsonResponseBase<>();
        try {
            ConsentTemplate consentTemplate = templateRepository.findFirstByTempName(request.getTempName());
            if (consentTemplate == null) {
                resp.setStatus(SystemCodeEnum.NOT_FOUND.getCode());
                resp.setMessage(SystemCodeEnum.NOT_FOUND.getMessage() + " " + request.getTempName());
                return resp;
            }

            consentTemplate = ConsentTemplate.builder()
                    .tempName(request.getTempName())
                    .content(request.getContent())
                    .level1(request.getLevel1())
                    .level2(request.getLevel2())
                    .channel(request.getChannel())
                    .createdDate(request.getCreatedDate())
                    .createdBy(request.getCreatedBy())
                    .modifyDate(request.getModifyDate())
                    .status(request.getStatus())
                    .build();
            consentTemplate.setId(id);
            templateRepository.save(consentTemplate);
            resp.setData(ConsentTemplateResponseDTO.from(consentTemplate));
            resp.setStatus(SystemCodeEnum.SUCCESS.getCode());
            resp.setMessage(SystemCodeEnum.SUCCESS.getMessage());
            return resp;
        } catch (Exception e) {
            resp.setStatus(SystemCodeEnum.INTERNAL_SERVER_ERROR.getCode());
            resp.setMessage(e.getMessage());
            return resp;
        }
    }

    @Override
    public void delete(Long id) {
        if (!templateRepository.existsById(id)){
            throw new EntityNotFoundException("Template not found with id: " + id);

        }
        templateRepository.deleteById(id);
    }

    private ConsentTemplateDTO.Response toResponse(ConsentTemplate entity) {
        return ConsentTemplateDTO.Response.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .level1(entity.getLevel1())
                .level2(entity.getLevel2())
                .channel(entity.getChannel())
                .createdDate(entity.getCreatedDate())
                .createdBy(entity.getCreatedBy())
                .modifyDate(entity.getModifyDate())
                .status(entity.getStatus())
                .build();
    }

    private ConsentTemplate toEntity(ConsentTemplateDTO.Request request) {
        return ConsentTemplate.builder()
                .content(request.getContent())
                .level1(request.getLevel1())
                .level2(request.getLevel2())
                .channel(request.getChannel())
                .createdDate(request.getCreatedDate())
                .createdBy(request.getCreatedBy())
                .modifyDate(request.getModifyDate())
                .status(request.getStatus())
                .build();
    }
}
