package ji.groupcloud.dto;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "account_role")
@IdClass(AccountRoleKey.class)
@Data
public class AccountRole {
    @Id
    @JoinColumn(name = "account", referencedColumnName = "id")
    private Integer id;

    @Id
    @Column(nullable = false)
    private String role;
}
