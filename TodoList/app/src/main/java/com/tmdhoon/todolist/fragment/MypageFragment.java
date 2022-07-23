package com.tmdhoon.todolist.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import com.tmdhoon.todolist.Api.ApiProvider;
import com.tmdhoon.todolist.Api.ServerApi;
import com.tmdhoon.todolist.Lobby.SignInActivity;
import com.tmdhoon.todolist.R;
import com.tmdhoon.todolist.Recyclerview.MyTodoAdapter;
import com.tmdhoon.todolist.Response.MyResponse;
import com.tmdhoon.todolist.Response.MyTodoResponse;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MypageFragment extends Fragment {

    private TextView username;
    private TextView userid;
    private TextView userage;
    private ArrayList<MyTodoResponse> todos;

    private MyTodoAdapter myTodoAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup containter,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_mypage, containter, false);

        username = rootView.findViewById(R.id.tvuser_name);
        userid = rootView.findViewById(R.id.tvuser_id);
        userage = rootView.findViewById(R.id.tvuser_age);

        todos = new ArrayList<>();
        RecyclerView recyclerView = rootView.findViewById(R.id.myRecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        myTodoAdapter = new MyTodoAdapter(todos);

        recyclerView.setAdapter(myTodoAdapter);

        ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);

        serverApi.my(SignInActivity.AccessToken).enqueue(new Callback<MyResponse>() {

            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    username.setText(response.body().getUser_name());
                    userid.setText(response.body().getUser_id());
                    userage.setText(String.valueOf(response.body().getUser_age()));

                    todos.addAll(response.body().getArrayList());
                    myTodoAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {}
        });

        return rootView;

    }
}