package com.hichat.common.util;


import java.util.List;

public class ReturnResult<T> {
    // 定义jackson对象


    // 响应业务状态

    private Integer status;

    // 响应消息

    private String msg;

    // 响应中的数据

    private T data;

    public static ReturnResult build(Integer status, String msg, Object data) {
        return new ReturnResult(status, msg, data);
    }

    public static ReturnResult ok(Object data) {
        return new ReturnResult(data);
    }

    public static ReturnResult ok() {
        return new ReturnResult(null);
    }

    public ReturnResult() {

    }

    public static ReturnResult build(Integer status, String msg) {
        return new ReturnResult(status, msg, null);
    }

    public ReturnResult(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ReturnResult(T data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
