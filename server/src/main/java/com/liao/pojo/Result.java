package com.liao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//服务器统一响应结果
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {

    private Integer code;
    private String msg;
    private T data;

    public static<E> Result<E> success(E data) {
        return new Result<>(0, "request success", data);
    }

    public static Result success() {
        return new Result(0, "request success", null);
    }

    public static Result error(String msg) {
        return new Result(1, msg, null);
    }

}
