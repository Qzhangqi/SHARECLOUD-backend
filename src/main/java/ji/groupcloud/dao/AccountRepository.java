package ji.groupcloud.dao;

import ji.groupcloud.dto.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    List<Account> findAllByUsername(@Param("username") String username);

    @Modifying
    @Transactional
    void deleteAllByUsername(@Param("username") String username);
}
