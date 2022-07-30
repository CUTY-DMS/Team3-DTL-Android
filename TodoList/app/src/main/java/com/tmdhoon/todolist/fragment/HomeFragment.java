package com.tmdhoon.todolist.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tmdhoon.todolist.Api.ApiProvider;
import com.tmdhoon.todolist.Api.ServerApi;
import com.tmdhoon.todolist.Lobby.SignInActivity;
import com.tmdhoon.todolist.R;
import com.tmdhoon.todolist.Adapter.TodoAdapter;
import com.tmdhoon.todolist.Response.MainResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private TodoAdapter todoAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    List<MainResponse> mainResponseList;

    public View onCreateView(LayoutInflater inflater, ViewGroup containter,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, containter, false);

        mainResponseList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recyclerview);

        linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);

        todoAdapter = new TodoAdapter(mainResponseList);

        recyclerView.setAdapter(todoAdapter);

        ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);

        serverApi.main(SignInActivity.AccessToken).enqueue(new Callback<List<MainResponse>>() {
            @Override
            public void onResponse(Call<List<MainResponse>> call, Response<List<MainResponse>> response) {
                mainResponseList.addAll(response.body());
                todoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<MainResponse>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        swipeRefreshLayout = view.findViewById(R.id.refresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                serverApi.main(SignInActivity.AccessToken).enqueue(new Callback<List<MainResponse>>() {
                    @Override
                    public void onResponse(Call<List<MainResponse>> call, Response<List<MainResponse>> response) {
                        mainResponseList.clear();
                        mainResponseList.addAll(response.body());
                        todoAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<MainResponse>> call, Throwable t) {
                        Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

}