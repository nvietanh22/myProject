package vn.lottefinance.pdms_core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.lottefinance.pdms_core.domain.PartnerAttachment;
import vn.lottefinance.pdms_core.domain.PartnerManagement;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartnerAttachmentRepository extends JpaRepository<PartnerAttachment, Long> {
    Optional<List<PartnerAttachment>> findByPartnerManagement(PartnerManagement partnerManagement);

}
