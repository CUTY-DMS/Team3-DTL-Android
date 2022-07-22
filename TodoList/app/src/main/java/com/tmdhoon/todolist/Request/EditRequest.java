package com.tmdhoon.todolist.Request;

public class EditRequest {
    private String title;
    private String content;

    public EditRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
