package com.tmdhoon.todolist.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tmdhoon.todolist.R;

public class MypageFragment extends Fragment {

   public View onCreateView(LayoutInflater inflater, ViewGroup containter,
                            Bundle savedInstanceState){
       ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_mypage, containter, false);

       return rootView;
   }
}