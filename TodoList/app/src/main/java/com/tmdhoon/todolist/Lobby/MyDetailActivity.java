package com.tmdhoon.todolist.Lobby;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.tmdhoon.todolist.Api.ApiProvider;
import com.tmdhoon.todolist.Api.ServerApi;
import com.tmdhoon.todolist.Lobby.SignInActivity;
import com.tmdhoon.todolist.Request.EditRequest;
import com.tmdhoon.todolist.databinding.ActivityMyDetailBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDetailActivity extends AppCompatActivity {

    private ActivityMyDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();

        binding.eteditTitle.addTextChangedListener(new TextWatcher() {                              // 제목 수정란에 변화가 있을때
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {       // 입력 전

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {           // 입력 중
                binding.tvedittitleCount.setText(binding.eteditTitle.getText().length() + "/20");   // 제목의 길이를 텍스트뷰에 띄움
                if(binding.eteditTitle.getText().length() == 20) binding.tvedittitleCount.setTextColor(Color.RED);  // 길이가 20인 경우 빨간색
                else binding.tvedittitleCount.setTextColor(Color.BLACK);                            // 아닌 경우 검정색
            }

            @Override
            public void afterTextChanged(Editable editable) {                                       // 입력 후

            }
        });

        binding.eteditContent.addTextChangedListener(new TextWatcher() {                            // 내용란에 변화가 있을때
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {       // 입력 전

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {           // 입력 중
                binding.tveditcontentCount.setText(binding.eteditContent.getText().length() + "/100");  // 내용의 길이를 텍스트뷰에 띄움
                if(binding.eteditContent.getText().length() == 100) binding.tveditcontentCount.setTextColor(Color.RED); // 내용의 길이가 100인 경우 빨간색
                else binding.tvedittitleCount.setTextColor(Color.BLACK);                            // 아닌 경우 검정색
            }

            @Override
            public void afterTextChanged(Editable editable) {                                       // 입력 후

            }
        });

        long id = (long) extras.get("id");                                                          // 인텐트로 넘어온 값을 저장하는 변수
        String Title = extras.getString("title");
        String Content = extras.getString("content");

        binding.eteditTitle.setText(Title);                                                         // 원래 제목을 띄움
        binding.eteditContent.setText(Content);                                                     // 원래 내용을 띄움

        binding.bteditPost.setOnClickListener(new View.OnClickListener() {                          // 수정 버튼을 눌렀을때
            @Override
            public void onClick(View view) {
                String editTitle = binding.eteditTitle.getText().toString();                        // 제목 변수
                String editContent = binding.eteditContent.getText().toString();                    // 내용 변수

                if(editTitle.length() == 0){
                    Toast.makeText(MyDetailActivity.this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show(); // 메시지
                }
                else if(editContent.length() == 0){                                                 // 내용의 길이가 0인 경우
                    Toast.makeText(MyDetailActivity.this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show(); // 메시지
                }
                else{                                                                               // 둘 다 아닌 경우
                    EditRequest editRequest = new EditRequest(editTitle, editContent);              // 요청 값에 넣고

                    ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);        // 레트로핏 가져와서

                    serverApi.edit(SignInActivity.AccessToken, id, editRequest).enqueue(new Callback<Void>() {  // 서버와 통신
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()){                                            // 응답 성공인 경우
                                Toast.makeText(MyDetailActivity.this, "게시글이 수정되었습니다!", Toast.LENGTH_SHORT).show();  // 메시지
                            }
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {                       // 응답 실패
                            Toast.makeText(MyDetailActivity.this, "관리자에게 문의해주세요", Toast.LENGTH_SHORT).show();   // 메시지
                        }
                    });
                }
            }
        });

        binding.bteditDelete.setOnClickListener(new View.OnClickListener() {                        // 삭제 버튼을 눌렀을때
            @Override
            public void onClick(View view) {
                binding.eteditTitle.setText("");                                                    // 제목 입력란 초기화
                binding.eteditContent.setText("");                                                  // 내용 입력란 초기화
            }
        });

    }
}