package ji.groupcloud.bean;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "account")
@Data
public class Account {
    @Id
    @Column(nullable = false)
    private String username;

    private String password;

    private String inviter;
}
