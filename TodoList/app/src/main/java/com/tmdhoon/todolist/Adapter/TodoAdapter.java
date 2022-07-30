package com.tmdhoon.todolist.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tmdhoon.todolist.Api.ApiProvider;
import com.tmdhoon.todolist.Api.ServerApi;
import com.tmdhoon.todolist.Lobby.DetailActivity;
import com.tmdhoon.todolist.Lobby.SignInActivity;
import com.tmdhoon.todolist.R;
import com.tmdhoon.todolist.Response.MainResponse;
import com.tmdhoon.todolist.fragment.HomeFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private List<MainResponse> list;

    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;

    public static String id;


    public class TodoViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView created_at;
        public TextView member_id;
        public TextView like_count;
        public ImageView todo_success;
        public ImageView like;

        public TodoViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tvtitle);
            created_at = view.findViewById(R.id.tvcreated_at);
            member_id = view.findViewById(R.id.tvmember_id);
            like_count = view.findViewById(R.id.tvlike_count);
            todo_success = view.findViewById(R.id.ivSuccess);
            like = view.findViewById(R.id.ivLike);
        }
    }

    public TodoAdapter(List<MainResponse> list) {                                                    // 어댑터 생성자
        this.list = list;
    }

    @NonNull
    @Override
    public TodoAdapter.TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todolist_recyclerview, parent, false);

        preferences = view.getContext().getSharedPreferences("UserLike", Context.MODE_PRIVATE);
        editor = preferences.edit();

        id = SignInActivity.preferences.getString("Id", "");

        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.TodoViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.created_at.setText(list.get(position).getCreated_at());
        holder.member_id.setText(list.get(position).getMember_id());
        holder.like_count.setText(String.valueOf(list.get(position).getLike_count()));

        if (preferences.getInt("Like" + id + list.get(position).getId(), 0) == 1) {
            holder.like.setImageResource(R.drawable.red);
        } else {
            holder.like.setImageResource(R.drawable.white);
        }

        if (list.get(position).getTodo_success() == true){
            holder.todo_success.setImageResource(R.drawable.correct);
        } else{
            holder.todo_success.setImageResource(R.drawable.incorrect);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);

                intent.putExtra("title", list.get(position).getTitle());
                intent.putExtra("content", list.get(position).getContent());
                intent.putExtra("member_id", list.get(position).getMember_id());
                intent.putExtra("created_at", list.get(position).getCreated_at());
                intent.putExtra("like_count", list.get(position).getLike_count());
                intent.putExtra("success", list.get(position).getTodo_success());
                intent.putExtra("id", list.get(position).getId());
                intent.putExtra("like", preferences.getInt("Like" + id + list.get(position).getId(), 0));

                view.getContext().startActivity(intent);
            }
        });

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);

                serverApi.like(SignInActivity.AccessToken, list.get(position).getId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (preferences.getInt("Like" + id + list.get(position).getId(), 0) == 1) {
                            holder.like.setImageResource(R.drawable.white);
                            editor.putInt("Like" + id + list.get(position).getId(), 0).commit();
                        } else if (preferences.getInt("Like" + id + list.get(position).getId(), 0) == 0) {
                            holder.like.setImageResource(R.drawable.red);
                            editor.putInt("Like" + id + list.get(position).getId(), 1).commit();
                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {                                                                     // 리스트의 크기 반환
        return list.size();
    }
}
