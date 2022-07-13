package com.tmdhoon.todolist.Response;

import java.util.Collection;

public class MainResponse {

    private long id;
    private String content;
    private String title;
    private String created_at;
    private String member_id;

    public MainResponse(long id, String content, String title, String created_at, String member_id) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.created_at = created_at;
        this.member_id = member_id;
    }

    public long getId(){
        return id;
    }

    public String getContents() {
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
}
