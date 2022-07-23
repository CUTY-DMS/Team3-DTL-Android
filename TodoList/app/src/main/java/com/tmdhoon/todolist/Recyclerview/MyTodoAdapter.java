package com.tmdhoon.todolist.Recyclerview;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tmdhoon.todolist.Api.ApiProvider;
import com.tmdhoon.todolist.Api.ServerApi;
import com.tmdhoon.todolist.Lobby.SignInActivity;
import com.tmdhoon.todolist.Lobby.MyDetailActivity;
import com.tmdhoon.todolist.R;
import com.tmdhoon.todolist.Response.MyTodoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTodoAdapter extends RecyclerView.Adapter<MyTodoAdapter.MyTodoViewHolder> {

    private List<MyTodoResponse> arrayList;

    public MyTodoAdapter(List<MyTodoResponse> arrayList) {
        this.arrayList = arrayList;
    }

    public class MyTodoViewHolder extends RecyclerView.ViewHolder {

        public TextView tvmyContent;
        public TextView tvmyTitle;
        public TextView tvmyCreated_at;
        public ImageView ivedit;
        public ImageView ivdelete;

        public MyTodoViewHolder(@NonNull View itemView) {
            super(itemView);

            tvmyContent = itemView.findViewById(R.id.tvmyContent);
            tvmyTitle = itemView.findViewById(R.id.tvmyTitle);
            tvmyCreated_at = itemView.findViewById(R.id.tvmyCreated_at);
            ivedit = itemView.findViewById(R.id.ivedit);
            ivdelete = itemView.findViewById(R.id.ivdelete);
        }
    }
    @NonNull
    @Override
    public MyTodoAdapter.MyTodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_recyclerview, parent, false);

        return new MyTodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTodoAdapter.MyTodoViewHolder holder, int position) {
        holder.tvmyContent.setText(arrayList.get(position).getContent());
        holder.tvmyTitle.setText(arrayList.get(position).getTitle());
        holder.tvmyCreated_at.setText(arrayList.get(position).getCreated_at());

        holder.ivedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MyDetailActivity.class);
                intent.putExtra("id", arrayList.get(position).getId());
                intent.putExtra("title", arrayList.get(position).getTitle());
                intent.putExtra("content", arrayList.get(position).getContent());
                view.getContext().startActivity(intent);
            }
        });

        holder.ivdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);

                serverApi.delete(SignInActivity.AccessToken, arrayList.get(position).getId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(view.getContext(), "성공적으로 삭제되었습니다", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(view.getContext(), "관리자에게 문의해주세요", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
