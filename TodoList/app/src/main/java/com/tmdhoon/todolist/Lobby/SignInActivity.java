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

    public static SharedPreferences preferences;                                                    // 아이디와 비밀번호를 꺼내 쓰기 위한 SharedPreferences
    public static SharedPreferences.Editor editor;                                                  // 아이디와 비밀번호를 저장하기 위한 editor

    public static String AccessToken;                                                               // 외부 클래스에서 접근 가능한 토큰

    private String userId;
    private String userPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);                         // shardpreferences 정의 이름 : UserInfo, 모드 : MODE_PRIVATE (현재 앱에서만 사용 O)
        editor = preferences.edit();                                                                // preferences 의 editor 정의

        binding.etId.addTextChangedListener(new TextWatcher() {                                     // 아이디의 길이 수를 세기 위한 부분
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {       // 입력 전

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {           // 입력 중
                int length = binding.etId.getText().length();                                       // 아이디 길이

                binding.tvidCount.setText(length + "/20");                                          // TextView 에 표시함

                if(length == 20) binding.tvidCount.setTextColor(Color.RED);                         // 길이가 최대인 경우
                else binding.tvidCount.setTextColor(Color.BLACK);                                   // TextView 를 빨간색으로.
            }

            @Override
            public void afterTextChanged(Editable editable) {                                       // 입력 후

            }
        });

        binding.btLogin.setOnClickListener(new View.OnClickListener() {                             // 로그인 버튼 클릭시
            @Override
            public void onClick(View view) {
                signinCheck();                                                                      // signinCheck 함수 불러옴
            }
        });

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {                             // 로그인 텍스트뷰 클릭시
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);          // 회원가입 페이지로 넘어감
                startActivity(intent);
                finish();
            }
        });

        if(preferences.getInt("Check", 0) == 1) {                                              // preferences 체크 값이 1인 경우
            autoLogin();                                                                            // 자동 로그인 함수 호출
        }
    }

    public void signinCheck(){                                                                     // signinCheck 함수
        userId = binding.etId.getText().toString();                                                 // Id 값 받아옴
        userPw = binding.etPw.getText().toString();                                                 // Pw 값 받아옴

        if(userId.length() == 0){                                                                   // Id 값이 없을 경우
            Toast.makeText(SignInActivity.this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show(); // 메시지
        }
        else if(userPw.length() == 0){                                                              // Pw 값이 없을 경우
            Toast.makeText(SignInActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show(); // 메시지
        }
        else signIn();                                                                              // 둘 다 아닌 경우 signIn 함수 호출

    }

    public void signIn() {                                                                          // signIn 함수
        SignInRequest signInRequest = new SignInRequest(userId, userPw);                            // 서버에 보낼 값

        ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);                    // ApiProvider 에서 만든 레트로핏

        serverApi.signIn(signInRequest).enqueue(new Callback<SignInResponse>() {                    // 서버로부터 응답
            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                if (response.isSuccessful()) {                                                      // 응답 성공
                    Toast.makeText(SignInActivity.this, "로그인에 성공했습니다!", Toast.LENGTH_SHORT).show(); // 메시지

                    AccessToken = response.body().getToken();                                       // 토큰을 받음

                    if(binding.cbautoLogin.isChecked()){                                            // 만약 자동 로그인이 체크되어있는경우
                        int check1 = 1;                                                             // check1 변수에 1을 저장
                        editor.putInt("Check", check1).commit();                                 // check1 변수를 앱 내부에 저장

                        editor.putString("Id", userId).commit();                                 // Id 값을 앱 내부에 저장
                        editor.putString("Pw", userPw).commit();                                 // Pw 값을 앱 내부에 저장
                    }

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);        // 메인 화면으로 넘어감
                    startActivity(intent);
                    finish();

                } else if (response.code() == 404) {                                                // 만약 응답 코드가 404인 경우
                    Toast.makeText(SignInActivity.this, "아이디를 확인해주세요", Toast.LENGTH_SHORT).show();  // 메시지
                }
            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {                         // 응답 실패
                Toast.makeText(SignInActivity.this, "로그인에 실패했습니다", Toast.LENGTH_SHORT).show();  // 메시지
            }
        });
    }

    public void autoLogin() {                                                                       // autoLogin 함수

        binding.cbautoLogin.setChecked(true);                                                       // 자동 로그인 버튼을 체크됨으로 바꿈

        binding.etId.setText(preferences.getString("Id", ""));                                // 아이디 입력란에 아이디를 띄우고
        binding.etPw.setText(preferences.getString("Pw", ""));                                // 비밀번호 입력한에 비밀번호를 띄움

        binding.tvidCount.setText(preferences.getString("Id", "").length() + "/20");

        String userId = binding.etId.getText().toString();                                          // 아이디 값을 얻어옴
        String userPw = binding.etPw.getText().toString();                                          // 비밀번호 값을 얻어옴

        SignInRequest signInRequest = new SignInRequest(userId, userPw);                            // request 값에 담고

        ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);                    // 레트로핏을 가져와서

        serverApi.signIn(signInRequest).enqueue(new Callback<SignInResponse>() {                    // 서버로 보냄
            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {  // 응답 성공
                if (response.isSuccessful()) {                                                      // 응답 코드가 성공
                    Toast.makeText(SignInActivity.this, "로그인에 성공했습니다!", Toast.LENGTH_SHORT).show(); // 메시지

                    AccessToken = response.body().getToken();                                       // 토큰을 받음

                    if (binding.cbautoLogin.isChecked()) {                                          // 로그인 유지가 체크되어있다면
                        int check1 = 1;                                                             // check1 변수를 1로 설정
                        editor.putInt("Check", check1).commit();                                 // check1 변수를 앱 내부에 저장
                        editor.putString("Id", userId).commit();                                 // 아이디 값을 앱 내부에 저장
                        editor.putString("Pw", userPw).commit();                                 // 비밀번호 값을 내부에 저장
                    }

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);        // 메인 액티비티로 넘어감
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