package com.tmdhoon.todolist.Response;

public class SignInResponse {
    private String msg;
    public String token;

    public SignInResponse(String msg, String token) {
        this.msg = msg;
        this.token = token;
    }

    public String getMsg() {
        return msg;
    }

    public String getToken() {
        return token;
    }
}
