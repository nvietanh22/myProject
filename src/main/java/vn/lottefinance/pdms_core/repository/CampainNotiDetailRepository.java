package vn.lottefinance.pdms_core.repository;

import vn.lottefinance.pdms_core.domain.CampainNotiDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampainNotiDetailRepository extends JpaRepository<CampainNotiDetail, Long> {
    Optional<List<CampainNotiDetail>> findCampainNotiDetailsByStatus(String status);

}
