package ji.groupcloud.dao;

import ji.groupcloud.dto.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRoleRepository extends JpaRepository<AccountRole, Integer> {
    List<AccountRole> findAllByUsername(@Param("username") String username);
}
