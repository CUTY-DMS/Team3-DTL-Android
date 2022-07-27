package com.tmdhoon.todolist.Adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
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
import com.tmdhoon.todolist.Lobby.MyDetailActivity;
import com.tmdhoon.todolist.R;
import com.tmdhoon.todolist.Response.MyTodoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTodoAdapter extends RecyclerView.Adapter<MyTodoAdapter.MyTodoViewHolder> {

    private List<MyTodoResponse> list;                                                              // 내가 작성한 투두 리스트 담음

    public MyTodoAdapter(List<MyTodoResponse> list) {                                               // 리스트 생성자
        this.list = list;
    }

    public class MyTodoViewHolder extends RecyclerView.ViewHolder {

        public TextView tvmyContent;                                                                // 내가 쓴 내용
        public TextView tvmyTitle;                                                                  // 내가 쓴 제목
        public TextView tvmyCreated_at;                                                             // 내가 쓴 날짜
        public TextView tvmySuccess;                                                                // 내 리스트의 성공여부
        public Button btSuccess;                                                                    // 완료 여부 전환 버튼
        public ImageView ivedit;                                                                    // 수정 이미지뷰
        public ImageView ivdelete;                                                                  // 삭제 이미지뷰

        public MyTodoViewHolder(@NonNull View itemView) {                                           // 아이디 연결
            super(itemView);

            tvmyContent = itemView.findViewById(R.id.tvmyContent);
            tvmyTitle = itemView.findViewById(R.id.tvmyTitle);
            tvmyCreated_at = itemView.findViewById(R.id.tvmyCreated_at);
            tvmySuccess = itemView.findViewById(R.id.tvmySuccess);
            btSuccess = itemView.findViewById(R.id.btSuccess);
            ivedit = itemView.findViewById(R.id.ivedit);
            ivdelete = itemView.findViewById(R.id.ivdelete);
        }
    }
    @NonNull
    @Override
    public MyTodoAdapter.MyTodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // 뷰 홀더를 만들때마다 호출, 뷰 홀더를 초기화

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_recyclerview, parent, false);  // my_recyclerview를 layoutinflater 를 이용하여 띄워줌

        return new MyTodoViewHolder(view);                                                          // 뷰 홀더 반환
    }

    @Override
    public void onBindViewHolder(@NonNull MyTodoAdapter.MyTodoViewHolder holder, int position) {    // ViewHolder를 데이터와 연결할 때 호출
        holder.tvmyContent.setText(list.get(position).getContent());                                // 내용 띄움
        holder.tvmyTitle.setText(list.get(position).getTitle());                                    // 제목 띄움
        holder.tvmyCreated_at.setText(list.get(position).getCreated_at());                          // 만든 날짜 띄움
        if(list.get(position).getSuccess() == true) holder.tvmySuccess.setText("O");                // boolean형의 성공 여부가 true 일 경우 O
        else holder.tvmySuccess.setText("X");                                                       // 그렇지 않으면 X

        holder.ivedit.setOnClickListener(new View.OnClickListener() {                               // 수정 버튼을 눌렀을때
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MyDetailActivity.class);              // 상세 액티비티로 인텐트
                intent.putExtra("id", list.get(position).getId());                            // 아이디
                intent.putExtra("title", list.get(position).getTitle());                      // 제목
                intent.putExtra("content", list.get(position).getContent());                  // 내용
                view.getContext().startActivity(intent);                                            // 상세 액티비티로 넘어감
            }
        });

        holder.btSuccess.setOnClickListener(new View.OnClickListener() {                            // 완료 여부 버튼을 눌렀을때
            @Override
            public void onClick(View view) {
                ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);            // 레트로핏 가져와서

                serverApi.success(SignInActivity.AccessToken, list.get(position).getId()).enqueue(new Callback<Void>() {    // 유저 토큰, 게시글 아이디를 함께 보냄
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });

        holder.ivdelete.setOnClickListener(new View.OnClickListener() {                             // 삭제 버튼을 눌렀을때
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                builder.setTitle("삭제");
                builder.setMessage("정말 삭제하시겠습니까?");

                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);

                        serverApi.delete(SignInActivity.AccessToken, list.get(position).getId()).enqueue(new Callback<Void>() { // 유저 토큰과 게시글 아이디를 보냄
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.isSuccessful()){                                                // 응답 성공인 경우
                                    list.remove(position);                                                  // 해당 포지션의 리스트를 삭제
                                    notifyItemRemoved(position);                                            // 리사이클러뷰 새로고침
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
