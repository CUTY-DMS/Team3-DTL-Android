package com.tmdhoon.todolist.Lobby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tmdhoon.todolist.Adapter.TodoAdapter;
import com.tmdhoon.todolist.Api.ApiProvider;
import com.tmdhoon.todolist.Api.ServerApi;
import com.tmdhoon.todolist.R;
import com.tmdhoon.todolist.databinding.ActivityDetailBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();

        String title = extras.getString("title");
        String content = extras.getString("content");
        String member_id = extras.getString("member_id");
        String created_at = extras.getString("created_at");
        long id = extras.getLong("id");
        int like_count = extras.getInt("like_count");
        boolean success = extras.getBoolean("success");

        binding.tvTitle.setText(title);
        binding.tvContent.setText(content);
        binding.tvMemeberId.setText(member_id);
        binding.tvCreatedAt.setText(created_at);
        binding.tvlikeCount.setText(String.valueOf(like_count));

        if(member_id.equals(SignInActivity.preferences.getString("Id", ""))){
            binding.ivdetailEdit.setImageResource(R.drawable.ic_baseline_edit_24);
            binding.ivdetailEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                    startActivity(intent);
                }
            });
        }

        if (success == true) {
            binding.ivdetailSuccess.setImageResource(R.drawable.correct);
        } else {
            binding.ivdetailSuccess.setImageResource(R.drawable.incorrect);
        }

        if (extras.getInt("like") == 1) {
            binding.ivdetailLike.setImageResource(R.drawable.red);
        } else {
            binding.ivdetailLike.setImageResource(R.drawable.white);
        }

        binding.ivdetailLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);

                serverApi.like(SignInActivity.AccessToken, id).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (TodoAdapter.preferences.getInt("Like" + TodoAdapter.id + id, 0) == 1) {
                            binding.ivdetailLike.setImageResource(R.drawable.white);
                            TodoAdapter.editor.putInt("Like" + TodoAdapter.id + id, 0).commit();
                        } else if (TodoAdapter.preferences.getInt("Like" + TodoAdapter.id + id, 0) == 0) {
                            binding.ivdetailLike.setImageResource(R.drawable.red);
                            TodoAdapter.editor.putInt("Like" + TodoAdapter.id + id, 1).commit();
                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });

    }
}