package vn.lottefinance.pdms_core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.lottefinance.pdms_core.domain.ConsentTemplate;

@Repository
public interface ConsentTemplateRepository extends JpaRepository<ConsentTemplate, Long> {
    ConsentTemplate findFirstByTempName(String tempName);
}
