package com.tmdhoon.todolist.Lobby;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.tmdhoon.todolist.Api.ApiProvider;
import com.tmdhoon.todolist.Api.ServerApi;
import com.tmdhoon.todolist.Lobby.SignInActivity;
import com.tmdhoon.todolist.Request.EditRequest;
import com.tmdhoon.todolist.databinding.ActivityMyDetailBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDetailActivity extends AppCompatActivity {

    private ActivityMyDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();

        binding.eteditTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.tvedittitleCount.setText(binding.eteditTitle.getText().length() + "/20");
                if(binding.eteditTitle.getText().length() == 20) binding.tvedittitleCount.setTextColor(Color.RED);
                else binding.tvedittitleCount.setTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.eteditContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.tveditcontentCount.setText(binding.eteditContent.getText().length() + "/100");
                if(binding.eteditContent.getText().length() == 100) binding.tveditcontentCount.setTextColor(Color.RED);
                else binding.tvedittitleCount.setTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        long id = (long) extras.get("id");
        String Title = extras.getString("title");
        String Content = extras.getString("content");

        binding.eteditTitle.setText(Title);
        binding.eteditContent.setText(Content);

        binding.bteditPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editTitle = binding.eteditTitle.getText().toString();
                String editContent = binding.eteditContent.getText().toString();

                if(editTitle.length() == 0)
                    Toast.makeText(MyDetailActivity.this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                else if(editContent.length() == 0)
                    Toast.makeText(MyDetailActivity.this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                else{
                    EditRequest editRequest = new EditRequest(editTitle, editContent);

                    ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);

                    serverApi.edit(SignInActivity.AccessToken, id, editRequest).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful())
                                Toast.makeText(MyDetailActivity.this, "게시글이 수정되었습니다!", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(MyDetailActivity.this, "관리자에게 문의해주세요", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        binding.bteditDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.eteditTitle.setText("");
                binding.eteditContent.setText("");
            }
        });

    }
}