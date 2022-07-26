package com.tmdhoon.todolist.Adapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

    private List<MainResponse> list;                                                                // MainResponse에서 만든 데이터를 list 형식으로 받아옴

    public class TodoViewHolder extends RecyclerView.ViewHolder {                                   // myrecyclerview 에서 만든 객체를 어댑터와 연결

        public TextView content;                                                                    // 내용
        public TextView title;                                                                      // 제목
        public TextView created_at;                                                                 // 만든 시간
        public TextView member_id;                                                                  // 유저 아이디
        public TextView like_count;                                                                 // 좋아요 수
        public TextView todo_success;                                                               // 성공 여부
        public ImageView like;                                                                      // 좋아요 하트

        public TodoViewHolder(View view) {                                                          // 아이디 연결
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

    public TodoAdapter(List<MainResponse> list){                                                    // 어댑터 생성자
        this.list = list;
    }

    @NonNull
    @Override
    public TodoAdapter.TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // 뷰 홀더를 만들때마다 호출, 뷰 홀더를 초기화

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todolist_recyclerview, parent, false);    // todolist_recyclerview를 layoutinflater 를 이용하여 띄워줌

        return new TodoViewHolder(view);                                                            // todoviewholder 리턴
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.TodoViewHolder holder, int position) {        // ViewHolder를 데이터와 연결할 때 호출

        holder.content.setText(list.get(position).getContent());                                    // 해당 포지션에 해당하는 부분을 띄움
        holder.title.setText(list.get(position).getTitle());
        holder.created_at.setText(list.get(position).getCreated_at());
        holder.member_id.setText(list.get(position).getMember_id());
        holder.like_count.setText(String.valueOf(list.get(position).getLike_count()));
        if(list.get(position).getTodo_success() == true) holder.todo_success.setText("O");          // boolean 형식의 todosuccess 가 true 인 경우 O를 띄움
        else holder.todo_success.setText("X");                                                      // false 인 경우 X

        holder.itemView.setOnClickListener(new View.OnClickListener() {                             // 아이템을 클릭했을때
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), DetailActivity.class);                // 상세 액티비티로 인텐트

                intent.putExtra("title", list.get(position).getTitle());                      // 제목
                intent.putExtra("content", list.get(position).getContent());                  // 내용
                intent.putExtra("member_id", list.get(position).getMember_id());              // 유저 아이디
                intent.putExtra("created_at", list.get(position).getCreated_at());            // 만든 시간

                view.getContext().startActivity(intent);                                            // 상세 액티비티로 넘어감
            }
        });

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.like.setImageResource(R.drawable.red);

                ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);

                serverApi.like(SignInActivity.AccessToken, list.get(position).getId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(view.getContext(), "성공", Toast.LENGTH_SHORT).show();
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
