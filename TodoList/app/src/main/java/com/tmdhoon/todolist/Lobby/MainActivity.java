package com.tmdhoon.todolist.Lobby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.tmdhoon.todolist.R;
import com.tmdhoon.todolist.databinding.ActivityMainBinding;
import com.tmdhoon.todolist.fragment.HomeFragment;
import com.tmdhoon.todolist.fragment.MypageFragment;
import com.tmdhoon.todolist.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private FragmentManager fragmentManager = getSupportFragmentManager();                          // 프래그먼트를 교체하거나 추가, 삭제 하는데 사용되는 클래스

    private HomeFragment homeFragment = new HomeFragment();                                         // 홈 프래그먼트
    private SearchFragment searchFragment = new SearchFragment();                                   // 검색 프래그먼트
    private MypageFragment mypageFragment = new MypageFragment();                                   // 마이페이지 프래그먼트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentTransaction transaction;                                                            // FragmentManager를 통해 획득
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.framelayout, homeFragment).commitNowAllowingStateLoss();

        binding.bottomnavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){

                FragmentTransaction transaction = fragmentManager.beginTransaction();

                int id = item.getItemId();

                switch(id){
                    case R.id.menu_home:
                        transaction.replace(R.id.framelayout, homeFragment).commitAllowingStateLoss();
                        break;

                    case R.id.menu_search:
                        transaction.replace(R.id.framelayout, searchFragment).commitAllowingStateLoss();
                        break;

                    case R.id.menu_mypage:
                        transaction.replace(R.id.framelayout, mypageFragment).commitAllowingStateLoss();
                        break;
                }
                return true;
            }
        });

    }
}