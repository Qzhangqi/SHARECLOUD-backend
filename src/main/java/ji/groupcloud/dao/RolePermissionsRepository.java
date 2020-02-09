package ji.groupcloud.dao;

import ji.groupcloud.dto.RolePermissions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionsRepository extends JpaRepository<RolePermissions, Integer> {
}
