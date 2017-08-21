package com.example.demo.utils.Exception;

import com.example.demo.utils.Enums.ResultEnums;

/**
 * Created by cw on 2017/8/21.
 */
public class UserException extends RuntimeException {
    private Integer code;

    public UserException(ResultEnums resultEnums) {
        super(resultEnums.getMsg());
        this.code = resultEnums.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
