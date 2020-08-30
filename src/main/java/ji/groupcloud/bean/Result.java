package ji.groupcloud.bean;

import lombok.Data;

@Data
public class Result<T> {

    private String code;

    private String msg;

    private T data;

    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static final String SUCCESS = "0001";

    public static final String OTHER_FAIL = "0000";
}
