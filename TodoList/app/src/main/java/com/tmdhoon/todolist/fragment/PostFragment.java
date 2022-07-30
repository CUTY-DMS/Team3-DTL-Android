package com.tmdhoon.todolist.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.tmdhoon.todolist.Api.ApiProvider;
import com.tmdhoon.todolist.Api.ServerApi;
import com.tmdhoon.todolist.Lobby.SignInActivity;
import com.tmdhoon.todolist.R;
import com.tmdhoon.todolist.Request.PostRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostFragment extends Fragment {

    private EditText etTitle;
    private EditText etContent;

    private TextView btPost;
    private TextView btDelete;

    private TextView tvtitleCount;
    private TextView tvcontentCount;

    public View onCreateView(LayoutInflater inflater, ViewGroup containter,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_post, containter, false);

        etTitle = rootView.findViewById(R.id.etTitle);
        etContent = rootView.findViewById(R.id.etContent);
        btPost = rootView.findViewById(R.id.tvPost);
        btDelete = rootView.findViewById(R.id.tvDelete);
        tvtitleCount = rootView.findViewById(R.id.tvtitleCount);
        tvcontentCount = rootView.findViewById(R.id.tvcontentCount);

        etTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length = etTitle.length();
                tvtitleCount.setText(length + "/20");
                if (length == 20) {
                    tvtitleCount.setTextColor(Color.RED);
                } else {
                    tvtitleCount.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length = etContent.length();
                tvcontentCount.setText(length + "/100");
                if (length == 100) {
                    tvcontentCount.setTextColor(Color.RED);
                } else {
                    tvcontentCount.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postCheck();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("초기화");
                builder.setMessage("정말 초기화하시겠습니까?");

                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        etTitle.setText("");
                        etContent.setText("");

                        etTitle.requestFocus();

                        Toast.makeText(getActivity(), "초기화되었습니다!", Toast.LENGTH_SHORT).show();
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

        return rootView;
    }

    public void postCheck() {
        int titleLength = etTitle.getText().length();
        int contentLength = etContent.getText().length();

        if (titleLength == 0 && contentLength == 0) {
            Toast.makeText(getContext(), "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
        } else if (titleLength == 0 && contentLength != 0) {
            Toast.makeText(getContext(), "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
        } else if (titleLength != 0 && contentLength == 0) {
            Toast.makeText(getContext(), "내용을 작성해주세요", Toast.LENGTH_SHORT).show();
        }else
            post();

    }

    public void post() {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();

        PostRequest postRequest = new PostRequest(title, content);

        ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);

        serverApi.post(SignInActivity.AccessToken, postRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "글이 정상적으로 등록되었습니다", Toast.LENGTH_SHORT).show();
                    etTitle.setText("");
                    etContent.setText("");

                    etTitle.requestFocus();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "글 등록에 실패하였습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }
}