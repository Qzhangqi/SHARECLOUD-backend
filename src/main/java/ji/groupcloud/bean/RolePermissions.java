package ji.groupcloud.bean;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "role_permissions")
@Data
public class RolePermissions {
    @Id
    @JoinColumn(name = "account_role", referencedColumnName = "role")
    private String role;

    @Column(nullable = false)
    private String permission;
}
