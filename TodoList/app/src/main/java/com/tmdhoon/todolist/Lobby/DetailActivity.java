package com.tmdhoon.todolist.Lobby;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tmdhoon.todolist.Adapter.TodoAdapter;
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

        String title = extras.getString("title");
        String content = extras.getString("content");
        String member_id = extras.getString("member_id");
        String created_at = extras.getString("created_at");
        int like_count = extras.getInt("like_count");

        binding.tvTitle.setText(title);
        binding.tvContent.setText(content);
        binding.tvMemeberId.setText(member_id);
        binding.tvCreatedAt.setText(created_at);
        binding.tvlikeCount.setText(String.valueOf(like_count));

        if(extras.getInt("Like") == 1){
            binding.ivdetailLike.setImageResource(R.drawable.red);
        }else if(extras.getInt("Like") == 0){
            binding.ivdetailLike.setImageResource(R.drawable.white);
        }

    }
}