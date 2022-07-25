package com.tmdhoon.todolist.Request;

public class SignInRequest {

    private String userId;
    private String userPw;

    public SignInRequest(String userId, String userPw) {
        this.userId = userId;
        this.userPw = userPw;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPw() {
        return userPw;
    }

}
