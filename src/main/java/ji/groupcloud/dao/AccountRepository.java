package ji.groupcloud.dao;

import ji.groupcloud.dto.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    List<Account> findAllByUsername(@Param("username") String username);
}
