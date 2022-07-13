package com.tmdhoon.todolist.Request;

public class PostRequest {

    private String title;
    private String content;

    public PostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return content;
    }

}
