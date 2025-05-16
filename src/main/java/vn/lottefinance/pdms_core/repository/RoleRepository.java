package vn.lottefinance.pdms_core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.lottefinance.pdms_core.domain.Role;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    Role findByRoleName(String name);
    List<Role> findRolesByRoleNameIn(List<String> names);

    Role findRoleById(Long id);

}
