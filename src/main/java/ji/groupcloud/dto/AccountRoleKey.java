package ji.groupcloud.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountRoleKey implements Serializable {

    private Integer id;

    private String role;
}
