package ji.groupcloud.dao;

import ji.groupcloud.bean.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AccountRoleRepository extends JpaRepository<AccountRole, Integer> {
    List<AccountRole> findAllByUsername(@Param("username") String username);

    List<AccountRole> findAllByRole(@Param("role") String role);

    @Modifying
    @Transactional
    void deleteAllByUsername(@Param("username") String username);
}