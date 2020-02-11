package ji.groupcloud.dto;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "account")
@Data
public class Account {
    @Id
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String inviter;
}
