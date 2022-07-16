package com.tmdhoon.todolist.Response;

import java.util.List;

public class MyResponse {

    private String user_name;
    private String user_id;
    private long user_age;

    private String title;
    private String content;
    private String created_at;

    public MyResponse(String user_name, String user_id, long user_age, String title, String content, String created_at) {
        this.user_name = user_name;
        this.user_id = user_id;
        this.user_age = user_age;
        this.title = title;
        this.content = content;
        this.created_at = created_at;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public long getUser_age() {
        return user_age;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreated_at() {
        return created_at;
    }
}
