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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.etRegisterName.addTextChangedListener(new TextWatcher() {                           // 이름 입력란에 변화가 있을때
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {       // 입력 전

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {           // 입력 중
                int length = binding.etRegisterName.getText().length();                             // 길이 변수

                binding.tvnameCount.setText(length + "/10");                                        // 이름 카운트 텍스트 뷰에 길이 띄움

                if(length == 10) binding.tvnameCount.setTextColor(Color.RED);                       // 길이가 10인 경우 빨간색
                else binding.tvnameCount.setTextColor(Color.BLACK);                                 // 아닌 경우 검정색

            }

            @Override
            public void afterTextChanged(Editable editable) {                                       // 입력 후

            }
        });

        binding.etRegisterId.addTextChangedListener(new TextWatcher() {                             // 아이디 입력란에 변화가 있을때
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {       // 입력 전

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {           // 입력 중
                int length = binding.etRegisterId.getText().length();                               // 길이 변수

                binding.tvidCount.setText(length + "/20");                                          // 아이디 카운트 텍스트 뷰에 길이 띄움

                if(length == 20) binding.tvidCount.setTextColor(Color.RED);                         // 길이가 20인 경우 빨간색
                else binding.tvidCount.setTextColor(Color.BLACK);                                   // 아닌 경우 검정색
            }

            @Override
            public void afterTextChanged(Editable editable) {                                       // 입력 후

            }
        });

        binding.tvRegister.setOnClickListener(new View.OnClickListener() {                          // 로그인 텍스트 뷰를 클릭했을때
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);          // 로그인 화면으로 넘어감
                startActivity(intent);
                finish();
            }
        });

        binding.btRegister.setOnClickListener(new View.OnClickListener() {                          // 회원가입 버튼을 클릭했을때
            @Override
            public void onClick(View view) {
                signupCheck();                                                                      // 회원가입 확인 함수 호출
            }
        });
    }

    private void signupCheck() {                                                                    // 회원가입 확인 함수
        String userId = binding.etRegisterId.getText().toString();                                  // 아이디
        int userAge = Integer.parseInt(binding.etRegisterAge.getText().toString());                 // 나이
        String userName = binding.etRegisterName.getText().toString();                              // 이름
        String userPw = binding.etRegisterPw.getText().toString();                                  // 비밀번호

        if (userName.length() == 0 || userId.length() == 0 || userPw.length() == 0 || (int)(Math.log10(userAge)+1) == 0) {  // 항목중 길이가 0인것이 있는 경우
            Toast.makeText(SignUpActivity.this, "모든 항목을 입력해주세요", Toast.LENGTH_SHORT).show();    // 메시지
        } else signUp();                                                                            // 회원가입 메서드 호출

    }

    public void signUp() {                                                                          // 회원가입 메서드
        String userName = binding.etRegisterName.getText().toString();                              // 각각의 항목을 변수에 담음
        int userAge = Integer.parseInt(binding.etRegisterAge.getText().toString());
        String userId = binding.etRegisterId.getText().toString();
        String userPw = binding.etRegisterPw.getText().toString();

        SignUpRequest signUpRequest = new SignUpRequest(userName, userAge, userId, userPw);         // 요청 값에 넣고

        ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);                    // 레트로핏 가져와서

        serverApi.signUp(signUpRequest).enqueue(new Callback<SignUpResponse>() {                    // 서버와 통신
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.isSuccessful()) {                                                      // 응답 성공인 경우

                    Toast.makeText(SignUpActivity.this, "회원가입에 성공했습니다", Toast.LENGTH_SHORT).show(); // 메시지

                    Intent intent = new Intent(getApplicationContext(), SignInActivity.class);      // 로그인 액티비티로 넘어감
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {                         // 응답 실패
                Toast.makeText(SignUpActivity.this, "통신 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
}