package com.tmdhoon.todolist.Recyclerview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tmdhoon.todolist.Lobby.DetailActivity;
import com.tmdhoon.todolist.R;
import com.tmdhoon.todolist.Response.MainResponse;
import com.tmdhoon.todolist.fragment.HomeFragment;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder>{

    private List<MainResponse> list;

    public class TodoViewHolder extends RecyclerView.ViewHolder {

        public TextView content;
        public TextView title;
        public TextView created_at;
        public TextView member_id;

        public TodoViewHolder(View view) {
            super(view);
            content = view.findViewById(R.id.tvcontent);
            title = view.findViewById(R.id.tvtitle);
            created_at = view.findViewById(R.id.tvcreated_at);
            member_id = view.findViewById(R.id.tvmember_id);
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

        holder.content.setText(list.get(position).getContents());
        holder.title.setText(list.get(position).getTitle());
        holder.created_at.setText(list.get(position).getCreated_at());
        holder.member_id.setText(list.get(position).getMember_id());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TodoAdapter", "onClick");
                Intent intent = new Intent(view.getContext(), DetailActivity.class);

                intent.putExtra("title", list.get(position).getTitle());
                intent.putExtra("content", list.get(position).getContents());
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
