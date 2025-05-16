package vn.lottefinance.pdms_core.repository;

import vn.lottefinance.pdms_core.domain.CustomerConsentService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerConsentServiceRepository extends JpaRepository<CustomerConsentService, Long> {
}
