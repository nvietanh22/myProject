package vn.lottefinance.pdms_core.repository;

import vn.lottefinance.pdms_core.domain.CampainNoti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampainNotiRepository extends JpaRepository<CampainNoti, Long> {
}
