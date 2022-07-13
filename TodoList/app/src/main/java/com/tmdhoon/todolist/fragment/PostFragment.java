package com.tmdhoon.todolist.fragment;

import android.content.Intent;
import android.os.Bundle;
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

    public View onCreateView(LayoutInflater inflater, ViewGroup containter,
                            Bundle savedInstanceState){
       ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_post, containter, false);

       etTitle = rootView.findViewById(R.id.etTitle);
       etContent = rootView.findViewById(R.id.etContent);
       btPost = rootView.findViewById(R.id.btPost);

       btPost.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               post();
           }
       });
       
       return rootView;
    }
   
   public void post(){

       Log.d("PostFragment", "post()");

       String title = etTitle.getText().toString();
       String content = etContent.getText().toString();
       
       if(etTitle.getText().toString().length() == 0){
           Toast.makeText(getContext(), "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
       }if(title.length()>20){
           Toast.makeText(getContext(), "제목을 20자 이하로 작성해주세요", Toast.LENGTH_SHORT).show();
       }if(content.length() == 0){
           Toast.makeText(getContext(), "내용을 작성해주세요", Toast.LENGTH_SHORT).show();
       }if(content.length()>200){
           Toast.makeText(getContext(), "내용을 200자 이하로 작성해주세요", Toast.LENGTH_SHORT).show();
       }else{
            postStart();
       }
   }

   public void postStart(){

       Log.d("PostFragment", "postStart()");

       String title = etTitle.getText().toString();
       String content = etContent.getText().toString();

       PostRequest postRequest = new PostRequest(title, content);

       ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);

       Call<PostRequest> call = serverApi.post(SignInActivity.AccessToken, postRequest);
       call.enqueue(new Callback<PostRequest>() {
           @Override
           public void onResponse(Call<PostRequest> call, Response<PostRequest> response) {
               Log.d("PostFragment", "onResponse");
                if(response.isSuccessful()){
                    Log.d("PostFragment", "isSuccessful()");
                    Toast.makeText(getContext(), "글이 정상적으로 등록되었습니다", Toast.LENGTH_SHORT).show();
                    etTitle.setText(" ");
                    etContent.setText(" ");
                }
           }

           @Override
           public void onFailure(Call<PostRequest> call, Throwable t) {
               Log.d("PostFragment", "onFailure");
               Toast.makeText(getContext(), "글 등록에 실패하였습니다", Toast.LENGTH_SHORT).show();
           }
       });


   }
}