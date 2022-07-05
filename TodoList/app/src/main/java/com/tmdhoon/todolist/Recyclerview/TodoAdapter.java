package com.tmdhoon.todolist.Recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tmdhoon.todolist.R;
import com.tmdhoon.todolist.databinding.TodolistRecyclerviewBinding;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder>{

    private TodolistRecyclerviewBinding binding;
    private ArrayList<ReData> arrayList;

    public class TodoViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTodo;

        public TodoViewHolder(View view) {
            super(view);
            tvTodo = (TextView) view.findViewById(R.id.tvTodo);
        }
    }

    public TodoAdapter(ArrayList<ReData> arrayList){
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public TodoAdapter.TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todolist_recyclerview, parent, false);

        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.TodoViewHolder holder, int position) {

        holder.tvTodo.setText(arrayList.get(position).getTvTodo());


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
