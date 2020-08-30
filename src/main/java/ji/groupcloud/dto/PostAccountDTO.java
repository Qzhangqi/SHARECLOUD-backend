package ji.groupcloud.dto;

import lombok.Data;

@Data
public class PostAccountDTO {
    private String username;

    private String password;

    private String inviter;

    private String authcode;
}
