package vn.lottefinance.pdms_core.repository;

import vn.lottefinance.pdms_core.domain.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfig, Long> {
    Optional<SystemConfig> findFirstByCodeAndStatus(String code, Integer status);
}
