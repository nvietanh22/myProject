package vn.lottefinance.pdms_core.service.core;

import vn.lottefinance.pdms_core.service.core.dto.campain.CampainNotiDetailDTO;

import java.util.List;

public interface CampainNotiDetailService {
    List<CampainNotiDetailDTO.Response> findAll();
    CampainNotiDetailDTO.Response findById(Long id);
    CampainNotiDetailDTO.Response create(CampainNotiDetailDTO.Request request);
    CampainNotiDetailDTO.Response update(Long id, CampainNotiDetailDTO.Request request);
    void delete(Long id);
}
