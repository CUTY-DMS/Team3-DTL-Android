package com.tmdhoon.todolist.Response;

public class MainResponse {

    private long id;
    private String content;
    private String title;
    private String created_at;
    private String member_id;
    private int like_count;
    private Boolean todo_success;

    public MainResponse(long id, String content, String title, String created_at, String member_id, int like_count, Boolean todo_success) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.created_at = created_at;
        this.member_id = member_id;
        this.like_count = like_count;
        this.todo_success = todo_success;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getMember_id() {
        return member_id;
    }

    public int getLike_count() {
        return like_count;
    }

    public Boolean getTodo_success() {
        return todo_success;
    }
}
