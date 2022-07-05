package com.tmdhoon.todolist.Lobby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tmdhoon.todolist.Api.ApiProvider;
import com.tmdhoon.todolist.Api.ServerApi;
import com.tmdhoon.todolist.SignUp.SignUpRequest;
import com.tmdhoon.todolist.SignUp.SignUpResponse;
import com.tmdhoon.todolist.databinding.ActivitySignupBinding;
import com.tmdhoon.todolist.databinding.FragmentMypageBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private FragmentMypageBinding binding1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        binding1 = FragmentMypageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signup();
            }
        });



    }



    private void Signup(){
        String userName = binding.etRegisterName.getText().toString();
        int userAge = Integer.parseInt(binding.etRegisterAge.getText().toString());
        String userId = binding.etRegisterId.getText().toString();
        String userPw = binding.etRegisterPw.getText().toString();
        String userPwcheck = binding.etRegisterPwCheck.getText().toString();

        if(userName.length() == 0 || userId.length() == 0 || userPw.length() == 0 || (int)(Math.log10(userAge)+1) == 0 || userPwcheck.length() == 0){
            Toast.makeText(SignUpActivity.this, "모든 항목을 입력해주세요", Toast.LENGTH_SHORT).show();
        }else {
            SignUpResponse();
        }
    }

    public void SignUpResponse(){
        String userName = binding.etRegisterName.getText().toString().trim();
        int userAge = Integer.parseInt(binding.etRegisterAge.getText().toString());
        String userId = binding.etRegisterId.getText().toString().trim();
        String userPw = binding.etRegisterPw.getText().toString().trim();

        SignUpRequest signUpRequest = new SignUpRequest(userName, userAge, userId, userPw);
        ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);

        serverApi.Signup(signUpRequest).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if(response.code() == 200){
                    Toast.makeText(SignUpActivity.this, "회원가입에 성공했습니다", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(SignUpActivity.this, "오류", Toast.LENGTH_SHORT).show();
                }

                finish();
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "통신 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }


}