package vn.lottefinance.pdms_core.repository;

import vn.lottefinance.pdms_core.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
}
