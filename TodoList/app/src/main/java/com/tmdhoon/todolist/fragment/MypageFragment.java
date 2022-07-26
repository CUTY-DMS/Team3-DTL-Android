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

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.tmdhoon.todolist.Api.ApiProvider;
import com.tmdhoon.todolist.Api.ServerApi;
import com.tmdhoon.todolist.Lobby.SignInActivity;
import com.tmdhoon.todolist.R;
import com.tmdhoon.todolist.Adapter.MyTodoAdapter;
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
    private ArrayList<MyTodoResponse> todos;                                                        // arraylist 에 mytodoresponse 값 담음
    private MyTodoAdapter myTodoAdapter;                                                            // mytodoadapter

    public View onCreateView(LayoutInflater inflater, ViewGroup containter,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_mypage, containter, false);

        username = rootView.findViewById(R.id.tvuser_name);                                         // 아이디 연결
        userid = rootView.findViewById(R.id.tvuser_id);
        userage = rootView.findViewById(R.id.tvuser_age);

        todos = new ArrayList<>();                                                                  // mytodoresponse 값을 arraylist
        RecyclerView recyclerView = rootView.findViewById(R.id.myRecyclerView);                     // 리사이클러뷰 연결
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());           // 리니어 레이아웃 매니저
        recyclerView.setLayoutManager(linearLayoutManager);                                         // 리니어 레이아웃 매니저 set

        myTodoAdapter = new MyTodoAdapter(todos);                                                   // 어댑터에 arraylist 담고

        recyclerView.setAdapter(myTodoAdapter);                                                     // 어댑터 설정

        Button btLogout = rootView.findViewById(R.id.btLogout);                                     // 버튼 아이디 연결

        btLogout.setOnClickListener(new View.OnClickListener() {                                    // 로그아웃 버튼 눌렀을때
            @Override
            public void onClick(View view) {

                SignInActivity.editor.putString("Id", "").commit();                           // 로그인 액티비티에서 만든 sharedpreferences Id 값을 날림
                SignInActivity.editor.putString("Pw", "").commit();                           // Pw 값 날림
                SignInActivity.editor.putInt("Check", 0).commit();                             // 체크 날림

                Intent intent = new Intent(getActivity(), SignInActivity.class);                    // 로그인 화면으로 넘어감
                startActivity(intent);

                Toast.makeText(getActivity(), "로그아웃 되었습니다!", Toast.LENGTH_SHORT).show();  // 메시지
            }
        });

        ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);                    // 레트로핏 가져와서

        serverApi.my(SignInActivity.AccessToken).enqueue(new Callback<MyResponse>() {               // 서버와 통신

            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {          // 응답 성공
                if (response.isSuccessful()) {                                                      // 응답 코드가 성공이 경우
                   
                    username.setText(response.body().getUser_name());                               // 내 이름 띄우고
                    userid.setText(response.body().getUser_id());                                   // 내 아이디 띄우고
                    userage.setText(String.valueOf(response.body().getUser_age()));                 // 내 나이 띄움

                    todos.addAll(response.body().getArrayList());                                   // 내가 작성한 투두 arraylist 에 추가
                    myTodoAdapter.notifyDataSetChanged();                                           // 리사이클러뷰 새로고침
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });

        return rootView;

    }
}