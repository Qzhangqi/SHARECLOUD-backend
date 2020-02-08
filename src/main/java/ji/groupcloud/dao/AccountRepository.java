package ji.groupcloud.dao;

import ji.groupcloud.dto.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}
