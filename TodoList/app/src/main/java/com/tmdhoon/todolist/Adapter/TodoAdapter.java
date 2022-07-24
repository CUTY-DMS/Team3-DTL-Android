package com.tmdhoon.todolist.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tmdhoon.todolist.Api.ApiProvider;
import com.tmdhoon.todolist.Api.ServerApi;
import com.tmdhoon.todolist.Lobby.DetailActivity;
import com.tmdhoon.todolist.Lobby.SignInActivity;
import com.tmdhoon.todolist.R;
import com.tmdhoon.todolist.Response.MainResponse;
import com.tmdhoon.todolist.databinding.TodolistRecyclerviewBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder>{

    private List<MainResponse> list;
    private TodolistRecyclerviewBinding binding;
    private TodoAdapter todoAdapter;

    public class TodoViewHolder extends RecyclerView.ViewHolder {

        public TextView content;
        public TextView title;
        public TextView created_at;
        public TextView member_id;
        public TextView like_count;
        public TextView todo_success;
        public ImageView like;

        public TodoViewHolder(View view) {
            super(view);
            content = view.findViewById(R.id.tvcontent);
            title = view.findViewById(R.id.tvtitle);
            created_at = view.findViewById(R.id.tvcreated_at);
            member_id = view.findViewById(R.id.tvmember_id);
            like_count = view.findViewById(R.id.tvlike_count);
            todo_success = view.findViewById(R.id.tvsuccess);
            like = view.findViewById(R.id.ivLike);
        }
    }

    public TodoAdapter(List<MainResponse> list){
        this.list = list;
    }

    @NonNull
    @Override
    public TodoAdapter.TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todolist_recyclerview, parent, false);

        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.TodoViewHolder holder, int position) {

        holder.content.setText(list.get(position).getContent());
        holder.title.setText(list.get(position).getTitle());
        holder.created_at.setText(list.get(position).getCreated_at());
        holder.member_id.setText(list.get(position).getMember_id());
        holder.like_count.setText(String.valueOf(list.get(position).getLike_count()));
        if(list.get(position).getTodo_success() == false) holder.todo_success.setText("O");
        else holder.todo_success.setText("X");

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), DetailActivity.class);

                intent.putExtra("title", list.get(position).getTitle());
                intent.putExtra("content", list.get(position).getContent());
                intent.putExtra("member_id", list.get(position).getMember_id());
                intent.putExtra("created_at", list.get(position).getCreated_at());

                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
