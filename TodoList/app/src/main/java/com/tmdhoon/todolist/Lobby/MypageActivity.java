package com.tmdhoon.todolist.Lobby;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tmdhoon.todolist.Api.ApiProvider;
import com.tmdhoon.todolist.Api.ServerApi;
import com.tmdhoon.todolist.Response.MyResponse;
import com.tmdhoon.todolist.databinding.ActivityMypageBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MypageActivity extends AppCompatActivity {

    private ActivityMypageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMypageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ServerApi serverApi = ApiProvider.getInstance().create(ServerApi.class);

        serverApi.my(SignInActivity.AccessToken).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if(response.isSuccessful()){
                    binding.tvmyName.setText(response.body().getUser_name());
                    binding.tvmyId.setText(response.body().getUser_id());
                    binding.tvmyAge.setText(String.valueOf(response.body().getUser_age()));
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });

        binding.cbDevelopers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DeveloperActivity.class);
                startActivity(intent);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        binding.cbLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("????????????");
                builder.setMessage("???????????? ???????????????????");

                builder.setPositiveButton("????????????!!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SignInActivity.editor.putString("Id", "").commit();
                        SignInActivity.editor.putString("Pw", "").commit();
                        SignInActivity.editor.putInt("Check", 0).commit();

                        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                        startActivity(intent);
                        finish();

                        Toast.makeText(getApplicationContext(), "???????????? ???????????????!", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}