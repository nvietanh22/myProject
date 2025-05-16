package vn.lottefinance.pdms_core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.lottefinance.pdms_core.domain.CampainNotiDetail;
import vn.lottefinance.pdms_core.domain.PartnerFormConfig;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartnerFormConfigRepository extends JpaRepository<PartnerFormConfig, Long> {
    Optional<List<PartnerFormConfig>> findFirstByFieldCode(String code);

}
