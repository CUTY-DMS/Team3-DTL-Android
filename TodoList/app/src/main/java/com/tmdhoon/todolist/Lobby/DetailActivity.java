package com.tmdhoon.todolist.Lobby;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tmdhoon.todolist.R;
import com.tmdhoon.todolist.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras =getIntent().getExtras();

        String Title = extras.getString("title");                                               // 제목을 받아옴
        String Content = extras.getString("content");                                           // 내용을 받아옴
        String Member_id = extras.getString("member_id");                                       // 유저 아이디를 받아옴
        String Created_at = extras.getString("created_at");                                     // 만든 시간을 받아옴

        binding.tvTitle.setText(Title);                                                             // 텍스트를 띄움
        binding.tvContent.setText(Content);
        binding.tvMemeberId.setText(Member_id);
        binding.tvCreatedAt.setText(Created_at);


    }
}