package com.tmdhoon.todolist.Request;

public class SignUpRequest {
    private String userName;
    private int userAge;
    private String userId;
    private String userPw;

    public SignUpRequest(String userName, int userAge, String userId, String userPw) {
        this.userName = userName;
        this.userAge = userAge;
        this.userId = userId;
        this.userPw = userPw;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPw() {
        return userPw;
    }
}
