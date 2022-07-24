package com.tmdhoon.todolist.Response;

public class MyTodoResponse {

    private String title;
    private String content;
    private String created_at;
    private long id;
    private Boolean success;

    public MyTodoResponse(String title, String content, String created_at, long id, Boolean success) {
        this.title = title;
        this.content = content;
        this.created_at = created_at;
        this.id = id;
        this.success = success;
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

    public long getId() {
        return id;
    }

    public Boolean getSuccess() {
        return success;
    }
}
