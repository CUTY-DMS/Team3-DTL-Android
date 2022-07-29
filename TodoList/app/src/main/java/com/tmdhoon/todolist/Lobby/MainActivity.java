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

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction transaction;

    private HomeFragment homeFragment = new HomeFragment();
    private PostFragment postFragment = new PostFragment();
    private MypageFragment mypageFragment = new MypageFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.framelayout, homeFragment).commitNowAllowingStateLoss();

        binding.bottomnavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){

                transaction = fragmentManager.beginTransaction();

                int id = item.getItemId();

                switch(id){
                    case R.id.menu_home:
                        transaction.replace(R.id.framelayout, homeFragment).commitAllowingStateLoss();
                        break;

                    case R.id.menu_post:
                        transaction.replace(R.id.framelayout, postFragment).commitAllowingStateLoss();
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