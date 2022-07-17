package com.tmdhoon.todolist.Lobby;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tmdhoon.todolist.Api.ApiProvider;
import com.tmdhoon.todolist.Api.ServerApi;
import com.tmdhoon.todolist.Request.SignUpRequest;
import com.tmdhoon.todolist.Response.SignUpResponse;
import com.tmdhoon.todolist.R;
import com.tmdhoon.todolist.databinding.ActivitySignupBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private String TAG;
    private Button btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.etRegisterName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length = binding.etRegisterName.getText().length();
                if(length == 10){
                    binding.tvnameCount.setTextColor(Color.RED);
                }else{
                    binding.tvnameCount.setTextColor(Color.BLACK);
                }
                binding.tvnameCount.setText(length + "/10");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etRegisterId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length = binding.etRegisterId.getText().length();
                if(length == 20){
                    binding.tvidCount.setTextColor(Color.RED);
                }else{
                    binding.tvidCount.setTextColor(Color.BLACK);
                }
                binding.tvidCount.setText(length + "/20");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btRegister = (Button) findViewById(R.id.btRegister);

        binding.btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Test", "fwdefewfwefwf");
                signUp();
            }
        });


    }

    private void signUp() {
        Log.e("Test", "singUp");
        String userId = binding.etRegisterId.getText().toString();
        int userAge = Integer.parseInt(binding.etRegisterAge.getText().toString());
        String userName = binding.etRegisterName.getText().toString();
        String userPw = binding.etRegisterPw.getText().toString();

        if (userName.length() == 0 || userId.length() == 0 || userPw.length() == 0 || (int)(Math.log10(userAge)+1) == 0) {
            Toast.makeText(SignUpActivity.this, "모든 항목을 입력해주세요", Toast.LENGTH_SHORT).show();
        } else {
            signUpResponse();
        }
    }

    public void signUpResponse() {
        String userName = binding.etRegisterName.getText().toString();
        int userAge = Integer.parseInt(binding.etRegisterAge.getText().toString());
        String userId = binding.etRegisterId.getText().toString();
        String userPw = binding.etRegisterPw.getText().toString();

        SignUpRequest signUpRequest = new SignUpRequest(userName, userAge, userId, userPw);

        ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);

        serverApi.signUp(signUpRequest).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.code() == 200) {

                    Log.e("Test", "success");

                    Toast.makeText(SignUpActivity.this, "회원가입에 성공했습니다", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Log.e("Test", "onFailure");
                Toast.makeText(SignUpActivity.this, "통신 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
}