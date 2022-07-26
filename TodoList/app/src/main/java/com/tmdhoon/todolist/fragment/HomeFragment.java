package com.tmdhoon.todolist.fragment;

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

public class HomeFragment extends Fragment{

    private RecyclerView recyclerView;                                                              // 리사이클러뷰
    private LinearLayoutManager linearLayoutManager;                                                // 리니어 레이아웃 매니저
    private TodoAdapter todoAdapter;                                                                // 리사이클러뷰 어댑터
    private SwipeRefreshLayout swipeRefreshLayout;                                                  // 당겨서 새로고침
    List<MainResponse> mainResponseList;                                                            // todolist

    public View onCreateView(LayoutInflater inflater, ViewGroup containter,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home, containter, false);

        mainResponseList = new ArrayList<>();                                                       // todoresponse를 arraylist에 담음
        recyclerView = view.findViewById(R.id.recyclerview);                                        // 리사이클러뷰 연결
        linearLayoutManager = new LinearLayoutManager(getActivity());                               // 리니어레이아웃 매니저 지정
        recyclerView.setLayoutManager(linearLayoutManager);                                         // 레이아웃매니저 설정

        todoAdapter = new TodoAdapter(mainResponseList);                                            // 어댑터에 서버로부터 받은 리스트를 담음

        recyclerView.setAdapter(todoAdapter);                                                       // 리사이클러뷰 어댑터 설정

        swipeRefreshLayout = view.findViewById(R.id.swipelayout);                                   // swipelayout 연결
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {        // 새로고침 할 때
            @Override
            public void onRefresh() {

                swipeRefreshLayout.setRefreshing(false);                                            // 새로고침 끝냄
            }
        });

        ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);                    // 레트로핏 생성

        serverApi.main(SignInActivity.AccessToken).enqueue(new Callback<List<MainResponse>>() {     // 서버로부터 리스트 받음
            @Override
            public void onResponse(Call<List<MainResponse>> call, Response<List<MainResponse>> response) {
                mainResponseList.addAll(response.body());                                           // 서버로부터 받은 값을 리스트에 모두 추가
                todoAdapter.notifyDataSetChanged();                                                 // 리사이클러뷰 새로고침
            }

            @Override
            public void onFailure(Call<List<MainResponse>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}