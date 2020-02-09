package ji.groupcloud.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountRoleKey implements Serializable {

    private String username;

    private String role;
}
