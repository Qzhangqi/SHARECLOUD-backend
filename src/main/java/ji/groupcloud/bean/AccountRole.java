package ji.groupcloud.bean;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "account_role")
@IdClass(AccountRoleKey.class)
@Data
public class AccountRole {
    @Id
    @JoinColumn(name = "account", referencedColumnName = "username")
    private String username;

    @Id
    @Column(nullable = false)
    private String role;
}
