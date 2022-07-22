package com.tmdhoon.todolist.Response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyResponse {

    private String user_name;
    private String user_id;
    private long user_age;
    private ArrayList<MyTodoResponse> todos;

    public MyResponse(String user_name, String user_id, long user_age, ArrayList<MyTodoResponse> todos) {
        this.user_name = user_name;
        this.user_id = user_id;
        this.user_age = user_age;
        this.todos = todos;
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

    public ArrayList<MyTodoResponse> getArrayList() {
        return todos;
    }
}
