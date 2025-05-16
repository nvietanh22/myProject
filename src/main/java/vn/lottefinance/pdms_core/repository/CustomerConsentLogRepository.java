package vn.lottefinance.pdms_core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.lottefinance.pdms_core.domain.CustomerConsent;
import vn.lottefinance.pdms_core.domain.CustomerConsentLog;

import java.util.Optional;

@Repository
public interface CustomerConsentLogRepository extends JpaRepository<CustomerConsentLog, Long> {
}
