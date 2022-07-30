package com.tmdhoon.todolist.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.Toast;


import com.tmdhoon.todolist.Api.ApiProvider;
import com.tmdhoon.todolist.Api.ServerApi;
import com.tmdhoon.todolist.Lobby.SignInActivity;
import com.tmdhoon.todolist.Lobby.MypageActivity;
import com.tmdhoon.todolist.R;
import com.tmdhoon.todolist.Adapter.MyTodoAdapter;
import com.tmdhoon.todolist.Response.MainResponse;
import com.tmdhoon.todolist.Response.MyResponse;
import com.tmdhoon.todolist.Response.MyTodoResponse;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MypageFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MyTodoAdapter myTodoAdapter;
    private SwipeRefreshLayout refreshLayout;

    private ImageView ivmyPage;

    ArrayList<MyTodoResponse> list;

    public View onCreateView(LayoutInflater inflater, ViewGroup containter,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_mypage, containter, false);

        list = new ArrayList<>();

        recyclerView = rootView.findViewById(R.id.myRecyclerView);

        linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);

        myTodoAdapter = new MyTodoAdapter(list);

        recyclerView.setAdapter(myTodoAdapter);

        ivmyPage = rootView.findViewById(R.id.ivmyPage);

        ivmyPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MypageActivity.class);
                startActivity(intent);
            }
        });

        ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);

        serverApi.my(SignInActivity.AccessToken).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.isSuccessful()) {
                    list.addAll(response.body().getArrayList());
                    myTodoAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });

        refreshLayout = rootView.findViewById(R.id.myrefresh);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                serverApi.my(SignInActivity.AccessToken).enqueue(new Callback<MyResponse>() {
                    @Override
                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                        if(response.isSuccessful()){
                            list.clear();
                            list.addAll(response.body().getArrayList());
                            myTodoAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<MyResponse> call, Throwable t) {

                    }
                });
                refreshLayout.setRefreshing(false);
            }
        });

        return rootView;

    }
}