package com.tmdhoon.todolist.Lobby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tmdhoon.todolist.Api.ApiProvider;
import com.tmdhoon.todolist.Api.ServerApi;
import com.tmdhoon.todolist.R;
import com.tmdhoon.todolist.Request.SignInRequest;
import com.tmdhoon.todolist.Response.SignInResponse;
import com.tmdhoon.todolist.databinding.ActivitySigninBinding;
import com.tmdhoon.todolist.databinding.FragmentMypageBinding;
import com.tmdhoon.todolist.databinding.FragmentPostBinding;
import com.tmdhoon.todolist.fragment.PostFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignInActivity extends AppCompatActivity {

    private ActivitySigninBinding binding;

    public static String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Test", "onClick");
                signIn();
            }
        });
    }

    private void signIn(){
        String userId = binding.etId.getText().toString();
        String userPw = binding.etPw.getText().toString();

        if(userId.length() == 0){
            Toast.makeText(SignInActivity.this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
        }else if(userPw.length() == 0){
            Toast.makeText(SignInActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
        }else{
            signInResponse();
        }
    }

    public void signInResponse() {
        String userId = binding.etId.getText().toString();
        String userPw = binding.etPw.getText().toString();

        SignInRequest signInRequest = new SignInRequest(userId, userPw);

        ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);

        serverApi.signIn(signInRequest).enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SignInActivity.this, "로그인에 성공했습니다!", Toast.LENGTH_SHORT).show();

                    token = response.body().getToken();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                } else if (response.code() == 404) {
                    Toast.makeText(SignInActivity.this, "아이디를 확인해주세요", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {
                Toast.makeText(SignInActivity.this, "로그인에 실패했습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }
}