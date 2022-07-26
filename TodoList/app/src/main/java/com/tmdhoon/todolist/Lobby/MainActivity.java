package com.tmdhoon.todolist.Lobby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.tmdhoon.todolist.R;
import com.tmdhoon.todolist.databinding.ActivityMainBinding;
import com.tmdhoon.todolist.fragment.HomeFragment;
import com.tmdhoon.todolist.fragment.MypageFragment;
import com.tmdhoon.todolist.fragment.PostFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private FragmentManager fragmentManager = getSupportFragmentManager();                          // 프래그먼트 교체, 이동, 삭제에 이용되는 프래그먼트 매니저

    private HomeFragment homeFragment = new HomeFragment();                                         // 홈 프래그먼트
    private PostFragment postFragment = new PostFragment();                                         // 검색 프래그먼트
    private MypageFragment mypageFragment = new MypageFragment();                                   // 마이페이지 프래그먼트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentTransaction transaction;                                                            // FragmentManager를 통해 획득
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.framelayout, homeFragment).commitNowAllowingStateLoss();           // 시작 프래그먼트가 보여질곳과 시작 프래그먼트 지정

        binding.bottomnavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){  // 바텀 내비게이션
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){

                FragmentTransaction transaction = fragmentManager.beginTransaction();

                int id = item.getItemId();                                                          // 바텀내비게이션 아이템 아이디를 얻어옴

                switch(id){                                                                         // 아이디에 따라
                    case R.id.menu_home:                                                            // 홈 프래그먼트
                        transaction.replace(R.id.framelayout, homeFragment).commitAllowingStateLoss();
                        break;

                    case R.id.menu_post:                                                            // 포스트 프래그먼트
                        transaction.replace(R.id.framelayout, postFragment).commitAllowingStateLoss();
                        break;

                    case R.id.menu_mypage:                                                          // 마이페이지 프래그먼트
                        transaction.replace(R.id.framelayout, mypageFragment).commitAllowingStateLoss();
                        break;
                }
                return true;
            }
        });

    }
}