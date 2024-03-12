package com.qingyou.auth.api;

import java.io.Serializable;

public record Result<T>(Boolean success, Integer code, String msg, T data) implements Serializable {

    public static <T> Result<T> error(String msg) {
        return new Result<>(false, 500, msg, null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(true, 200, null, data);
    }

}