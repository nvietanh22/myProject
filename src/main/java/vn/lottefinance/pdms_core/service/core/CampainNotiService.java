package vn.lottefinance.pdms_core.service.core;

import org.springframework.web.multipart.MultipartFile;
import vn.lottefinance.pdms_core.common.BaseResponseDTO;
import vn.lottefinance.pdms_core.service.core.dto.campain.CampainNotiDTO;

import java.util.List;

public interface CampainNotiService {
    List<CampainNotiDTO.Response> findAll();
    CampainNotiDTO.Response findById(Long id);
    CampainNotiDTO.Response create(CampainNotiDTO.Request request);
    CampainNotiDTO.Response update(Long id, CampainNotiDTO.Request request);
    void delete(Long id);

    CampainNotiDTO.UploadFileResponse processExtractCampainUpload(MultipartFile file, String fileName);

    BaseResponseDTO addNewCampain(CampainNotiDTO.AddRequest request);
}
