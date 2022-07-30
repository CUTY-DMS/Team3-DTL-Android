package com.tmdhoon.todolist.Adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.tmdhoon.todolist.Api.ApiProvider;
import com.tmdhoon.todolist.Api.ServerApi;
import com.tmdhoon.todolist.Lobby.SignInActivity;
import com.tmdhoon.todolist.Lobby.EditActivity;
import com.tmdhoon.todolist.R;
import com.tmdhoon.todolist.Response.MyTodoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTodoAdapter extends RecyclerView.Adapter<MyTodoAdapter.MyTodoViewHolder> {

    private List<MyTodoResponse> list;

    public MyTodoAdapter(List<MyTodoResponse> list) {                                               // 리스트 생성자
        this.list = list;
    }

    public class MyTodoViewHolder extends RecyclerView.ViewHolder {

        public TextView myContent;
        public TextView myTitle;
        public TextView myCreated_at;
        public ImageView mySuccess;
        public Button btSuccess;
        public ImageView edit;
        public ImageView delete;

        public MyTodoViewHolder(@NonNull View itemView) {
            super(itemView);

            myContent = itemView.findViewById(R.id.tvmyContent);
            myTitle = itemView.findViewById(R.id.tvmyTitle);
            myCreated_at = itemView.findViewById(R.id.tvmyCreated_at);
            mySuccess = itemView.findViewById(R.id.ivmySuccess);
            edit = itemView.findViewById(R.id.ivedit);
            delete = itemView.findViewById(R.id.ivdelete);
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
        holder.myContent.setText(list.get(position).getContent());
        holder.myTitle.setText(list.get(position).getTitle());
        holder.myCreated_at.setText(list.get(position).getCreated_at());
        if (list.get(position).getSuccess() == true) holder.mySuccess.setImageResource(R.drawable.correct);
        else holder.mySuccess.setImageResource(R.drawable.incorrect);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditActivity.class);
                intent.putExtra("id", list.get(position).getId());
                intent.putExtra("title", list.get(position).getTitle());
                intent.putExtra("content", list.get(position).getContent());
                view.getContext().startActivity(intent);
            }
        });

        holder.btSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);

                serverApi.success(SignInActivity.AccessToken, list.get(position).getId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                builder.setTitle("삭제");
                builder.setMessage("정말 삭제하시겠습니까?");

                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);

                        serverApi.delete(SignInActivity.AccessToken, list.get(position).getId()).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    list.remove(position);
                                    notifyItemRemoved(position);
                                    Toast.makeText(view.getContext(), "삭제되었습니다!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(view.getContext(), "관리자에게 문의해주세요", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {                                                                     // 리스트의 사이즈 반환
        return list.size();
    }


}
