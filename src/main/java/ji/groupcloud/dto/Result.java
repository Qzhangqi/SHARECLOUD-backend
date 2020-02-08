package ji.groupcloud.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class Result<T> {

    private Integer code;

    private String msg;

    private T data;
}
