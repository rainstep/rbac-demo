package com.example.rbacdemo.common;


public class Result<T> {
    private int code;
    private T data;
    private String msg;

    private Result(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return this.code == Code.SUCCESS;
    }

    public static <T> Result<T> success() {
        return new Result<T>(Code.SUCCESS, null, Message.SUCCESS);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(Code.SUCCESS, data, Message.SUCCESS);
    }

    public static Result error() {
        return new Result(Code.ERROR, null, Message.ERROR);
    }

    public static Result error(String msg) {
        return new Result(Code.ERROR, null, msg);
    }

    public static Result error(int code) {
        return new Result(code, null, Message.ERROR);
    }

    public static Result error(int code, String msg) {
        return new Result(code, null, msg);
    }

    public static Result unauthenticated() {
        return new Result(Code.UNAUTHENTICATED, null, Message.UNAUTHENTICATED);
    }

    public static class Code {
        public static final int SUCCESS = 1;
        public static final int ERROR = 0;
        public static final int UNAUTHENTICATED = 401;
        public static final int UNAUTHORIZED = 403;
    }

    public static class Message {
        public static final String SUCCESS = "success";
        public static final String ERROR = "error";
        public static final String UNAUTHENTICATED = "未认证";
        public static final String UNAUTHORIZED = "未授权";
    }

}
