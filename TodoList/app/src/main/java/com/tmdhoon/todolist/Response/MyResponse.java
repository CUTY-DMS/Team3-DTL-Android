package com.tmdhoon.todolist.Response;

public class MyResponse {

    private String user_name;
    private String user_id;
    private int user_age;

    public MyResponse(String user_name, String user_id, int user_age) {
        this.user_name = user_name;
        this.user_id = user_id;
        this.user_age = user_age;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public int getUser_age() {
        return user_age;
    }
}
