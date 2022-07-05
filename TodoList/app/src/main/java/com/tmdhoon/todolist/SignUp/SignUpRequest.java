package com.tmdhoon.todolist.SignUp;

public class SignUpRequest {
    private String userId;
    private int userAge;
    private String userName;
    private String userPw;

    public SignUpRequest(String userId, int userAge, String userName, String userPw) {
        this.userId = userId;
        this.userAge = userAge;
        this.userName = userName;
        this.userPw = userPw;
    }

    public String getUserId() {
        return userId;
    }

    public int getUserAge() {
        return userAge;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPw() {
        return userPw;
    }
}
