package com.tmdhoon.todolist.Lobby;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.tmdhoon.todolist.Api.ApiProvider;
import com.tmdhoon.todolist.Api.ServerApi;
import com.tmdhoon.todolist.Request.EditRequest;
import com.tmdhoon.todolist.databinding.ActivityEditBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity {

    private ActivityEditBinding binding;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();

        id = extras.getLong("id");
        String title = extras.getString("title");
        String content = extras.getString("content");

        binding.eteditTitle.setText(title);
        binding.eteditContent.setText(content);

        int titleLength = binding.eteditTitle.getText().length();
        int contentLength = binding.eteditContent.getText().length();

        binding.tvedittitleCount.setText(titleLength + "/20");
        binding.tveditcontentCount.setText(contentLength + "/100");

        binding.eteditTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length = binding.eteditTitle.length();
                binding.tvedittitleCount.setText(length + "/20");
                if (length == 20) {
                    binding.tvedittitleCount.setTextColor(Color.RED);
                }else {
                    binding.tvedittitleCount.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.eteditContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length = binding.eteditTitle.length();
                binding.tveditcontentCount.setText(length + "/100");
                if (length == 100) {
                    binding.tveditcontentCount.setTextColor(Color.RED);
                }else {
                    binding.tvedittitleCount.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.tveditPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editCheck();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        binding.tveditDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("초기화");
                builder.setMessage("정말 초기화하시겠습니까?");

                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        binding.eteditTitle.setText("");
                        binding.eteditContent.setText("");

                        binding.eteditTitle.requestFocus();

                        Toast.makeText(EditActivity.this, "초기화되었습니다!", Toast.LENGTH_SHORT).show();
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

    public void editCheck() {
        int titleLength = binding.eteditTitle.length();
        int contentLength = binding.eteditContent.length();
        if (titleLength == 0 && contentLength ==0) {
            Toast.makeText(EditActivity.this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
        } else if (titleLength == 0 && contentLength != 0) {
            Toast.makeText(EditActivity.this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
        } else if (titleLength != 0 && contentLength == 0) {
            Toast.makeText(EditActivity.this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
        } else {
            edit();
        }
    }

    public void edit() {
        String editTitle = binding.eteditTitle.getText().toString();
        String editContent = binding.eteditContent.getText().toString();

        EditRequest editRequest = new EditRequest(editTitle, editContent);

        ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);

        serverApi.edit(SignInActivity.AccessToken, id, editRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditActivity.this, "게시글이 수정되었습니다!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditActivity.this, "관리자에게 문의해주세요", Toast.LENGTH_SHORT).show();
            }
        });
    }

}