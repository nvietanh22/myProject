package vn.lottefinance.pdms_core.service.core.impl;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.lottefinance.pdms_core.config.JsonResponseBase;
import vn.lottefinance.pdms_core.config.SearchResult;
import vn.lottefinance.pdms_core.domain.CustomerConsent;
import vn.lottefinance.pdms_core.enums.SystemCodeEnum;
import vn.lottefinance.pdms_core.repository.CustomerConsentRepository;
import vn.lottefinance.pdms_core.service.core.CustomerConsentService;
import vn.lottefinance.pdms_core.service.core.dto.customerconsent.CustomerConsentDTO;
import vn.lottefinance.pdms_core.service.core.mapper.customerconsent.CustomerConsentMapper;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerConsentServiceImpl implements CustomerConsentService {

    private final CustomerConsentRepository consentRepository;

    public CustomerConsentServiceImpl(CustomerConsentRepository consentRepository) {
        this.consentRepository = consentRepository;
    }

    @Override
    public List<CustomerConsentDTO.Response> findAll() {
        return consentRepository.findAll().stream().map(CustomerConsentMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public CustomerConsentDTO.Response findById(Long id) {
        return consentRepository.findById(id).map(CustomerConsentMapper::toResponse).orElse(null);
    }

    @Override
    public CustomerConsentDTO.Response findByIdCard(String idCard) {
        return consentRepository.findFirstByIdCardNumber(idCard).map(CustomerConsentMapper::toResponse).orElse(null);
    }

    @Override
    public CustomerConsentDTO.Response create(CustomerConsentDTO.Request request) {
        CustomerConsent entity = CustomerConsentMapper.toEntity(request);
        return CustomerConsentMapper.toResponse(consentRepository.save(entity));
    }

    @Override
    public JsonResponseBase<CustomerConsentDTO.Response> update(Long id, CustomerConsentDTO.Request request) {
        JsonResponseBase<CustomerConsentDTO.Response> resp = new JsonResponseBase<>();
        try {
            CustomerConsent customerConsent = consentRepository.findFirstByIdCardNumberAndChannel(request.getIdCardNumber(), request.getChannel());
            if (customerConsent == null) {
                resp.setStatus(SystemCodeEnum.NOT_FOUND.getCode());
                resp.setMessage(SystemCodeEnum.NOT_FOUND.getMessage() + " " + request.getCustomerName());
                return resp;
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            customerConsent = CustomerConsent.builder()
                    .customerName(request.getCustomerName())
                    .idCardNumber(request.getIdCardNumber())
                    .phoneNumber(request.getPhoneNumber())
                    .email(request.getEmail())
                    .refId(request.getRefId())
                    .cifNumber(request.getCifNumber())
                    .createBy(customerConsent.getCreateBy())
                    .modifyBy(username)
                    .createdDate(request.getCreateDate())
                    .modifyDate(request.getModifyDate())
                    .dob(request.getDob())
                    .channel(request.getChannel())
                    .issuedDate(request.getIssuedDate())
                    .issuedPlace(request.getIssuedPlace())
                    .cusComment(request.getCusComment())
                    .reference(request.getReference())
                    .evidence(request.getEvidence())
                    .build();
            consentRepository.save(customerConsent);
            resp.setData(CustomerConsentDTO.Response.from(customerConsent));
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
    public JsonResponseBase<CustomerConsentDTO.CustomerAcceptResponse> created(CustomerConsentDTO.Request request) {
        JsonResponseBase<CustomerConsentDTO.CustomerAcceptResponse> resp = new JsonResponseBase<>();
        try {
            CustomerConsent customerConsent = consentRepository.findFirstByIdCardNumberAndChannel(request.getIdCardNumber(), request.getChannel());
            if (customerConsent != null) {
                resp.setStatus(SystemCodeEnum.DUPLICATE.getCode());
                resp.setMessage(SystemCodeEnum.DUPLICATE.getMessage() + " " + request.getIdCardNumber());
                return resp;
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            customerConsent = CustomerConsent.builder()
                    .customerName(request.getCustomerName())
                    .idCardNumber(request.getIdCardNumber())
                    .phoneNumber(request.getPhoneNumber())
                    .email(request.getEmail())
                    .refId(request.getRefId())
                    .cifNumber(request.getCifNumber())
                    .createBy(username)
                    .modifyBy(username)
                    .createdDate(request.getCreateDate())
                    .modifyDate(request.getModifyDate())
                    .dob(request.getDob())
                    .channel(request.getChannel())
                    .issuedDate(request.getIssuedDate())
                    .issuedPlace(request.getIssuedPlace())
                    .cusComment(request.getCusComment())
                    .reference(request.getReference())
                    .evidence(request.getEvidence())
                    .build();
            consentRepository.save(customerConsent);
            resp.setData(CustomerConsentDTO.CustomerAcceptResponse.from(customerConsent));
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
    public CustomerConsentDTO.Response inquiry(CustomerConsentDTO.InquiryRequest request) {
        CustomerConsent customerConsent = null;
        if (StringUtils.isEmpty(request.getCifNumber())) {
            customerConsent = consentRepository.findFirstByIdCardNumberAndPhoneNumber(request.getIdCardNumber(), request.getPhoneNumber()).orElse(null);
            return CustomerConsentMapper.toResponse(customerConsent);
        }

        customerConsent = consentRepository.findFirstByIdCardNumberAndCifNumberAndPhoneNumber(request.getIdCardNumber(), request.getCifNumber(), request.getPhoneNumber()).orElse(null);
        return CustomerConsentMapper.toResponse(customerConsent);
    }

    @Override
    public void delete(Long id) {
        consentRepository.deleteById(id);
    }

    @Override
    public SearchResult<List<CustomerConsentDTO.Response>> searching(CustomerConsentDTO.Search search) {
        SearchResult<List<CustomerConsentDTO.Response>> searchResult = new SearchResult<>();

        try {
            PageRequest pageable = PageRequest.of(search.getPage(), search.getRowsPerPage());
            Specification<CustomerConsent> specification = (root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();

                for (Field field : search.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    try {
                        Object value = field.get(search);
                        if (value != null && !value.toString().trim().isEmpty()) {
                            String fieldName = field.getName();
                            Path<?> path = root.get(fieldName);
                            Class<?> fieldType = path.getJavaType();

                            if (fieldType.equals(String.class)) {
                                predicates.add(criteriaBuilder.like(
                                        criteriaBuilder.upper(root.get(fieldName)),
                                        "%" + value.toString().trim().toUpperCase() + "%"
                                ));
                            } else if (fieldType.equals(LocalDateTime.class)) {
                                LocalDateTime dateValue = LocalDateTime.parse(value.toString());
                                predicates.add(criteriaBuilder.equal(root.get(fieldName), dateValue));
                            } else {
                                predicates.add(criteriaBuilder.equal(root.get(fieldName), value));
                            }
                        }
                    } catch (IllegalAccessException | DateTimeParseException e) {

                    }
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            };
            Page<CustomerConsent> page = consentRepository.findAll(specification, pageable);
            searchResult.setData(page.getContent()
                    .stream()
                    .map(CustomerConsentDTO.Response::from)
                    .collect(Collectors.toList()));
            searchResult.setTotalElements(page.getTotalElements());
            searchResult.setTotalPages(page.getTotalPages());
            searchResult.setStatus(SystemCodeEnum.SUCCESS.getCode());
            searchResult.setMessage(SystemCodeEnum.SUCCESS.getMessage());
            return searchResult;
        } catch (IllegalArgumentException e) {
            searchResult.setStatus(SystemCodeEnum.BAD_REQUEST.getCode());
            searchResult.setMessage(e.getMessage());
            return searchResult;
        } catch (Exception e) {
            searchResult.setStatus(SystemCodeEnum.INTERNAL_SERVER_ERROR.getCode());
            searchResult.setMessage(e.getMessage());
            return searchResult;
        }
    }
}