package com.tmdhoon.todolist.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.tmdhoon.todolist.R;

public class SearchFragment extends Fragment {

   public View onCreateView(LayoutInflater inflater, ViewGroup containter,
                            Bundle savedInstanceState){
       ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_search, containter, false);

       return rootView;
   }
}