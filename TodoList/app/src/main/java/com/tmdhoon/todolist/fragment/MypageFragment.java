package com.tmdhoon.todolist.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.TestLooperManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tmdhoon.todolist.Api.ApiProvider;
import com.tmdhoon.todolist.Api.ServerApi;
import com.tmdhoon.todolist.Lobby.SignInActivity;
import com.tmdhoon.todolist.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MypageFragment extends Fragment {

   private TextView username;
   private TextView userid;
   private TextView userage;

   private Button btLogin;


   public View onCreateView(LayoutInflater inflater, ViewGroup containter,
                            Bundle savedInstanceState){
       ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_mypage, containter, false);

       username = rootView.findViewById(R.id.tvuser_name);
       userid = rootView.findViewById(R.id.tvuser_id);
       userage = rootView.findViewById(R.id.tvuser_age);

       return rootView;

   }
}