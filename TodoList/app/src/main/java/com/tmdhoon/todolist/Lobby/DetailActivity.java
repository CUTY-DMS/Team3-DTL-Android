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

        String Title = extras.getString("title");
        String Content = extras.getString("content");
        String Member_id = extras.getString("member_id");
        String Created_at = extras.getString("created_at");

        binding.tvTitle.setText(Title);
        binding.tvContent.setText(Content);
        binding.tvMemeberId.setText(Member_id);
        binding.tvCreatedAt.setText(Created_at);


    }
}