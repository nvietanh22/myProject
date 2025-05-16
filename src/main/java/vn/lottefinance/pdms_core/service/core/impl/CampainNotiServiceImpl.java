package vn.lottefinance.pdms_core.service.core.impl;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import vn.lottefinance.pdms_core.common.BaseResponseDTO;
import vn.lottefinance.pdms_core.domain.CampainNoti;
import vn.lottefinance.pdms_core.domain.CampainNotiDetail;
import vn.lottefinance.pdms_core.enums.CampainFileEnum;
import vn.lottefinance.pdms_core.enums.CampainNotiStatusEnum;
import vn.lottefinance.pdms_core.exception.RestException;
import vn.lottefinance.pdms_core.repository.CampainNotiDetailRepository;
import vn.lottefinance.pdms_core.service.core.dto.campain.CampainNotiDTO;
import vn.lottefinance.pdms_core.repository.CampainNotiRepository;
import vn.lottefinance.pdms_core.service.core.CampainNotiService;
import org.springframework.stereotype.Service;
import vn.lottefinance.pdms_core.service.core.dto.campain.CampainNotiDetailDTO;
import vn.lottefinance.pdms_core.service.core.mapper.campainnoti.CampainNotiDetailMapper;
import vn.lottefinance.pdms_core.service.core.mapper.campainnoti.CampainNotiMapper;
import vn.lottefinance.pdms_core.util.ApachePoiExcelUtil;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CampainNotiServiceImpl implements CampainNotiService {

    @Autowired
    private CampainNotiRepository repository;

    @Autowired
    private CampainNotiDetailRepository detailRepository;


    @Override
    public List<CampainNotiDTO.Response> findAll() {
        return repository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public CampainNotiDTO.Response findById(Long id) {
        return repository.findById(id).map(this::toResponse).orElse(null);
    }

    @Override
    public CampainNotiDTO.Response create(CampainNotiDTO.Request request) {
        CampainNoti entity = toEntity(request);
        return toResponse(repository.save(entity));
    }

    @Override
    public CampainNotiDTO.Response update(Long id, CampainNotiDTO.Request request) {
        CampainNoti entity = toEntity(request);
        entity.setId(id);
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    /***
     * this method read file excel customer
     * @return CampainNotiDTO
     */
    @Override
    public CampainNotiDTO.UploadFileResponse processExtractCampainUpload(MultipartFile file, String fileName) {

        List<CampainNotiDetail> campainNotiDetailList = new ArrayList<>();
        List<CampainNotiDetail> campainNotiDetailErrorList = new ArrayList<>();

        int rowNumber = 0;
        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                CampainNotiDetail campainNotiDetail = CampainNotiDetail.builder()
                        .cifNumber(ApachePoiExcelUtil.getCellValue(row.getCell(CampainFileEnum.CIF_NUMBER.getPosition())))
                        .idCardNumber(ApachePoiExcelUtil.getCellValue(row.getCell(CampainFileEnum.IDCARD.getPosition())))
                        .customerName(ApachePoiExcelUtil.getCellValue(row.getCell(CampainFileEnum.CUST_NAME.getPosition())))
                        .phoneNumber(ApachePoiExcelUtil.getCellValue(row.getCell(CampainFileEnum.PHONE_NO.getPosition())))
                        .email(ApachePoiExcelUtil.getCellValue(row.getCell(CampainFileEnum.EMAIL.getPosition())))
                        .notiChannel(ApachePoiExcelUtil.getCellValue(row.getCell(CampainFileEnum.NOTI_CHANNEL.getPosition())))
                        .createdDate(LocalDateTime.now())
                        .createdBy("admin")
                        .status(CampainNotiStatusEnum.PENDING.getCode())
                        .build();

                ValidateCampainResult checkResult = validateCampainFile(campainNotiDetail);

                if (checkResult.isValid()) {
                    campainNotiDetailList.add(campainNotiDetail);
                } else {
                    campainNotiDetailList.add(campainNotiDetail);
                }

                rowNumber++;
            }


            List<CampainNotiDetailDTO.Response> campainNotiDetailDTOList =  new ArrayList<>();
            List<CampainNotiDetailDTO.Response> campainNotiDetailDTOErrorList =  new ArrayList<>();
            Long totalRecord = 0L;
            if (!campainNotiDetailList.isEmpty()) {
                campainNotiDetailDTOList = campainNotiDetailList.stream().map(CampainNotiDetailMapper::toDTO).collect(Collectors.toList());
                totalRecord += campainNotiDetailDTOList.size();
            }

            if (!campainNotiDetailErrorList.isEmpty()) {
                campainNotiDetailDTOErrorList = campainNotiDetailErrorList.stream().map(CampainNotiDetailMapper::toDTO).collect(Collectors.toList());
                totalRecord += campainNotiDetailDTOErrorList.size();
            }

            return CampainNotiDTO.UploadFileResponse.builder()
                    .campainNotiDTO(CampainNotiDTO.Response.builder()
                            .fileName(fileName)
                            .createdBy("admin")
                            .createdDate(LocalDateTime.now())
                            .totalRecord(totalRecord)
                            .build())
                    .campainNotiDetailDTOList(campainNotiDetailDTOList)
                    .campainNotiDetailDTOErrorList(campainNotiDetailDTOErrorList)
                    .build();
        } catch (Exception ex) {
            log.error("error upload file campain: {}", ex.getMessage());
            throw new RestException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    /***
     * this method create new campain
     * @return BaseResponseDTO
     */
    @Override
    public BaseResponseDTO addNewCampain(CampainNotiDTO.AddRequest request) {
        CampainNoti campainNoti = CampainNotiMapper.toEntity(request.getCampainNoti());
        List<CampainNotiDetail> campainNotiDetailList = request.getCampainNotiDetailList().isEmpty() ? new ArrayList<>() : request.getCampainNotiDetailList().stream().map( CampainNotiDetailMapper::toEntity).collect(Collectors.toList());

        if (campainNoti == null) {
            throw new RestException(HttpStatus.BAD_REQUEST, "Campain data not valid");
        }

        campainNoti.setCreatedBy("admin");
        campainNoti.setCreatedDate(LocalDateTime.now());
        campainNoti.setStatus("Pending");

        CampainNoti campainNotiSaved = repository.save(campainNoti);
        campainNotiDetailList.stream().forEach(item -> {
            item.setCampainNoti(campainNotiSaved);
            item.setCreatedDate(LocalDateTime.now());
            item.setCreatedBy("admin");
            item.setStatus(CampainNotiStatusEnum.PENDING.getCode());
        });

        detailRepository.saveAll(campainNotiDetailList);
        return BaseResponseDTO.builder()
                .data(campainNoti)
                .build();
    }

    private CampainNotiDTO.Response toResponse(CampainNoti entity) {
        return CampainNotiDTO.Response.builder()
            .id(entity.getId())
            .fileName(entity.getFileName())
            .totalRecord(entity.getTotalRecord())
            .createdBy(entity.getCreatedBy())
            .createdDate(entity.getCreatedDate())
            .modifyDate(entity.getModifyDate())
            .modifyDate(entity.getModifyDate())
            .status(entity.getStatus())
            .build();
    }

    private CampainNoti toEntity(CampainNotiDTO.Request request) {
        return CampainNoti.builder()
            .fileName(request.getFileName())
            .totalRecord(request.getTotalRecord())
            .createdBy(request.getCreatedBy())
            .createdDate(request.getCreatedDate())
            .modifyDate(request.getModifyDate())
            .modifyDate(request.getModifyDate())
            .status(request.getStatus())
            .build();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private class ValidateCampainResult {
        private String errors;
        private boolean isValid;
    }

    private ValidateCampainResult validateCampainFile(CampainNotiDetail campainNotiDetail) {
        String errorMessage = "";
        String comma = "";
        boolean isValid = true;
        if (StringUtils.isEmpty(campainNotiDetail.getIdCardNumber())) {
            errorMessage += comma + "idCard Empty";
            comma = ", ";
            isValid = false;
        }

        if (StringUtils.isEmpty(campainNotiDetail.getCustomerName())) {
            errorMessage += comma + "customer name Empty";
            comma = ", ";
            isValid = false;
        }

        if (StringUtils.isEmpty(campainNotiDetail.getPhoneNumber())) {
            errorMessage += comma + "phone number Empty";
            comma = ", ";
            isValid = false;
        }

        if (StringUtils.isEmpty(campainNotiDetail.getEmail())) {
            errorMessage += comma + "email Empty";
            comma = ", ";
            isValid = false;
        }

        return new ValidateCampainResult(errorMessage, isValid);
    }
}
