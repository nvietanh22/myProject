package vn.lottefinance.pdms_core.service.core;

import vn.lottefinance.pdms_core.service.core.dto.SystemConfigDTO;

import java.util.List;

public interface SystemConfigService {
    List<SystemConfigDTO.Response> findAll();
    SystemConfigDTO.Response findById(Long id);
    SystemConfigDTO.Response create(SystemConfigDTO.Request request);
    SystemConfigDTO.Response update(Long id, SystemConfigDTO.Request request);
    void delete(Long id);
}
