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

    public static SharedPreferences id;
    public static SharedPreferences pw;
    public static SharedPreferences check;

    public static SharedPreferences.Editor ideditor;
    public static SharedPreferences.Editor pweditor;
    public static SharedPreferences.Editor checkeditor;

    public static String AccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        id = getSharedPreferences("Id", MODE_PRIVATE);
        pw = getSharedPreferences("Pw", MODE_PRIVATE);
        check = getSharedPreferences("Check", MODE_PRIVATE);

        ideditor = id.edit();
        pweditor = pw.edit();
        checkeditor = check.edit();

        if(check.getInt("Check", 0) == 1){
            binding.cbautoLogin.setChecked(true);

            binding.etId.setText(id.getString("Id", ""));
            binding.etPw.setText(pw.getString("Pw", ""));

            Log.w("SignInActivity", "setText");

            String userId = binding.etId.getText().toString();
            String userPw = binding.etPw.getText().toString();

            binding.tvidCount.setText(binding.etId.getText().length() + "/20");

            SignInRequest signInRequest = new SignInRequest(userId, userPw);

            Log.w("SignInActivity", "signinrequest");

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
        }else binding.cbautoLogin.setChecked(false);

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

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
                finish();
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

        if(userId.length() == 0) Toast.makeText(SignInActivity.this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
        else if(userPw.length() == 0) Toast.makeText(SignInActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
        else signInResponse();

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

                    AccessToken = response.body().getToken();

                    if(binding.cbautoLogin.isChecked()){
                        int check = 1;
                        checkeditor.putInt("Check", check);

                        ideditor.putString("Id", userId);
                        pweditor.putString("Pw", userPw);
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
}