package vn.lottefinance.pdms_core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.lottefinance.pdms_core.domain.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findPermissionByName(String name);
    Permission findPermissionById(Long id);
}
