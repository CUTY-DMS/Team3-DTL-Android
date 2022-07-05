package com.tmdhoon.todolist.Lobby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tmdhoon.todolist.Api.ApiProvider;
import com.tmdhoon.todolist.Api.ServerApi;
import com.tmdhoon.todolist.SignIn.SignInRequest;
import com.tmdhoon.todolist.SignIn.SignInResponse;
import com.tmdhoon.todolist.databinding.ActivitySigninBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignInActivity extends AppCompatActivity {

    private ActivitySigninBinding binding;

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
                SignIn();
            }
        });
    }

    private void SignIn(){
        String userId = binding.etId.getText().toString();
        String userPw = binding.etPw.getText().toString();

        if(userId.length() == 0){
            Toast.makeText(SignInActivity.this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
        }else if(userPw.length() == 0){
            Toast.makeText(SignInActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
        }else{
            SignInResponse();
        }
    }

    public void SignInResponse(){
        String userId = binding.etId.getText().toString();
        String userPw = binding.etPw.getText().toString();

        SignInRequest signInRequest = new SignInRequest(userId, userPw);
        ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);

        serverApi.Signin(signInRequest).enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                if(response.code() == 200){
                    Toast.makeText(SignInActivity.this, "로그인에 성공했습니다!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {
                Toast.makeText(SignInActivity.this, "로그인에 실패했습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }

}