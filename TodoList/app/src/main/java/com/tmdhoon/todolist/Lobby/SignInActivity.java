package com.tmdhoon.todolist.Lobby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tmdhoon.todolist.Api.ApiProvider;
import com.tmdhoon.todolist.Api.ServerApi;
import com.tmdhoon.todolist.Request.SignInRequest;
import com.tmdhoon.todolist.Response.SignInResponse;
import com.tmdhoon.todolist.databinding.ActivitySigninBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignInActivity extends AppCompatActivity {

    private ActivitySigninBinding binding;

    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;

    public static String AccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        editor = preferences.edit();

        binding.etId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length = binding.etId.getText().length();

                binding.tvidCount.setText(length + "/20");

                if(length == 20) binding.tvidCount.setTextColor(Color.RED);
                else binding.tvidCount.setTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signinCheck();
            }
        });

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        if(preferences.getInt("Check", 0) == 1) {
            autoLogin();
        }
    }

    public void signinCheck(){
        int idLength = binding.etId.length();
        int pwLength = binding.etPw.length();

        if(idLength == 0 && pwLength == 0){
            Toast.makeText(SignInActivity.this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
        }
        else if(idLength == 0 &&  pwLength != 0){
            Toast.makeText(SignInActivity.this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
        }
        else if(idLength != 0 &&  pwLength == 0){
            Toast.makeText(SignInActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
        }
        else signIn();

    }

    public void signIn() {
        String userId = binding.etId.getText().toString();
        String userPw = binding.etPw.getText().toString();

        SignInRequest signInRequest = new SignInRequest(userId, userPw);

        ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);

        serverApi.signIn(signInRequest).enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SignInActivity.this, "로그인에 성공했습니다!", Toast.LENGTH_SHORT).show();

                    AccessToken = response.body().getToken();

                    if(binding.cbautoLogin.isChecked()){
                        editor.putInt("Check", 1).commit();
                        editor.putString("Id", userId).commit();
                        editor.putString("Pw", userPw).commit();
                    }

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();

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

    public void autoLogin() {

        binding.cbautoLogin.setChecked(true);

        binding.etId.setText(preferences.getString("Id", ""));
        binding.etPw.setText(preferences.getString("Pw", ""));

        binding.tvidCount.setText(preferences.getString("Id", "").length() + "/20");

        String userId = binding.etId.getText().toString();
        String userPw = binding.etPw.getText().toString();

        SignInRequest signInRequest = new SignInRequest(userId, userPw);

        ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);

        serverApi.signIn(signInRequest).enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SignInActivity.this, "로그인에 성공했습니다!", Toast.LENGTH_SHORT).show();

                    AccessToken = response.body().getToken();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {
            }
        });
    }
}