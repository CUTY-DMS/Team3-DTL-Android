package com.tmdhoon.todolist.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.tmdhoon.todolist.Api.ApiProvider;
import com.tmdhoon.todolist.Api.ServerApi;
import com.tmdhoon.todolist.Lobby.SignInActivity;
import com.tmdhoon.todolist.R;
import com.tmdhoon.todolist.Request.PostRequest;
import com.tmdhoon.todolist.Response.PostResponse;
import com.tmdhoon.todolist.Response.SignInResponse;
import com.tmdhoon.todolist.databinding.FragmentPostBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostFragment extends Fragment {
    
    private EditText etTitle;
    private EditText etContent;
    private Button btPost;
    private Button btDelete;
    private TextView tvtitleCount;
    private TextView tvcontentCount;

    public View onCreateView(LayoutInflater inflater, ViewGroup containter,
                            Bundle savedInstanceState){
       ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_post, containter, false);

       etTitle = rootView.findViewById(R.id.etTitle);
       etContent = rootView.findViewById(R.id.etContent);
       btPost = rootView.findViewById(R.id.btPost);
       btDelete = rootView.findViewById(R.id.btDelete);
       tvtitleCount = rootView.findViewById(R.id.tvtitleCount);
       tvcontentCount = rootView.findViewById(R.id.tvcontentCount);

       etTitle.addTextChangedListener(new TextWatcher() {                                           // 제목 입력란에 변화가 있을때
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {        // 입력 전

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {            // 입력 중
               tvtitleCount.setText(etTitle.getText().length() + "/20");                            // 제목의 길이를 얻어와서 텍스트뷰에 표시
               if(etTitle.getText().length() == 20) tvtitleCount.setTextColor(Color.RED);           // 제목이 20자인 경우 빨간색
               else tvtitleCount.setTextColor(Color.BLACK);                                         // 그렇지 않은 경우 검정색
           }

           @Override
           public void afterTextChanged(Editable editable) {                                        // 입력 후

           }
       });

       etContent.addTextChangedListener(new TextWatcher() {                                         // 내용 입력란에 변화가 있을때
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {        // 입력 전

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {            // 입력 중
               tvcontentCount.setText(etContent.getText().length() + "/100");                       // 내용의 길이를 얻어와서 텍스트뷰에 표시
               if(etContent.getText().length() == 100) tvcontentCount.setTextColor(Color.RED);      // 내용이 100자인 경우 빨간색
               else tvcontentCount.setTextColor(Color.BLACK);                                       // 그렇지 않은 경우 검정색
           }

           @Override
           public void afterTextChanged(Editable editable) {                                        // 입력 후

           }
       });

       btPost.setOnClickListener(new View.OnClickListener() {                                       // 게시 버튼을 눌렀을때
           @Override
           public void onClick(View view) {
               postCheck();                                                                         // postCheck 메서드 호출
           }
       });

       btDelete.setOnClickListener(new View.OnClickListener() {                                     // 삭제 버튼을 눌렀을때
           @Override
           public void onClick(View view) {
               etTitle.setText("");                                                                 // ""으로 설정
               etContent.setText("");                                                               // ""으로 설정
           }
       });
       
       return rootView;
    }
   
   public void postCheck(){
       String title = etTitle.getText().toString();                                                 // 제목 얻어옴
       String content = etContent.getText().toString();                                             // 내용 얻어옴
       
       if(title.length() == 0){                                                                     // 제목 길이가 0인 경우
           Toast.makeText(getContext(), "제목을 입력해주세요", Toast.LENGTH_SHORT).show();         // 메시지
       }
       if(content.length() == 0){                                                                   // 내용 길이가 0인 경우
           Toast.makeText(getContext(), "내용을 작성해주세요", Toast.LENGTH_SHORT).show();         // 메시지
       }
       else post();                                                                                 // 둘 다 아닌 경우 post 메서드 호출

   }

   public void post(){
       String title = etTitle.getText().toString();                                                 // 제목 얻어옴
       String content = etContent.getText().toString();                                             // 내용 얻어옴

       PostRequest postRequest = new PostRequest(title, content);                                   // request 에 넣고

       ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);                     // 레트로핏 가져와서

       serverApi.post(SignInActivity.AccessToken, postRequest).enqueue(new Callback<PostRequest>() {// 서버와 통신
           @Override
           public void onResponse(Call<PostRequest> call, Response<PostRequest> response) {         // 응답 성공
                if(response.isSuccessful()){
                    Toast.makeText(getContext(), "글이 정상적으로 등록되었습니다", Toast.LENGTH_SHORT).show(); // 메시지
                    etTitle.setText("");                                                            // 입력란 초기화
                    etContent.setText("");
                }
           }

           @Override
           public void onFailure(Call<PostRequest> call, Throwable t) {                             // 응답 실패
               Toast.makeText(getContext(), "글 등록에 실패하였습니다", Toast.LENGTH_SHORT).show(); // 메시지
           }
       });


   }
}