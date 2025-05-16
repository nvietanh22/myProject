package vn.lottefinance.pdms_core.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.lottefinance.pdms_core.domain.CustomerConsent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerConsentRepository extends JpaRepository<CustomerConsent, Long>, JpaSpecificationExecutor<CustomerConsent> {
    Optional<CustomerConsent> findFirstByIdCardNumber(String idCard);
    Optional<CustomerConsent> findFirstByIdCardNumberAndCifNumberAndPhoneNumber(String idCard, String cifNumber, String phoneNumber);
    Optional<CustomerConsent> findFirstByIdCardNumberAndPhoneNumber(String idCard, String phoneNumber);
    CustomerConsent findFirstByIdCardNumberAndChannel(String idCardNumber, String channel);
}
