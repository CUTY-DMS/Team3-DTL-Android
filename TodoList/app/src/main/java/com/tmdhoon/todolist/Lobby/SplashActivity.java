package com.tmdhoon.todolist.Lobby;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.tmdhoon.todolist.R;
import com.tmdhoon.todolist.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Handler handler = new Handler();                                                            // 쓰레드간의 통신을 도와줌
        handler.postDelayed(new Runnable() {                                                        // 약간의 시간을 두고 실행시킴
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);          // 로그인 화면으로 넘어감
                startActivity(intent);
                finish();
            }
        }, 2000);                                                                           // 2초 후
    }
}
