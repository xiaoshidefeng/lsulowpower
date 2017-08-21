package com.example.demo.utils;

import com.example.demo.utils.Enums.ResultEnums;

/**
 * Created by cw on 2017/7/19.
 */
public class ResultUtil {
    public static Result success(Object object) {
        Result result =new Result();
        result.setCode(200);
        result.setMsg("success");
        result.setData(object);

        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(ResultEnums resultEnums) {
        Result result = new Result();
        result.setCode(resultEnums.getCode());
        result.setMsg(resultEnums.getMsg());

        return result;
    }
}
