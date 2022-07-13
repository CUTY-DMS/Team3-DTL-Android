package com.tmdhoon.todolist.Api;

import com.tmdhoon.todolist.Request.PostRequest;
import com.tmdhoon.todolist.Request.SignInRequest;
import com.tmdhoon.todolist.Response.MainResponse;
import com.tmdhoon.todolist.Response.SignInResponse;
import com.tmdhoon.todolist.Request.SignUpRequest;
import com.tmdhoon.todolist.Response.SignUpResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ServerApi {

    @POST("users/signin")
    Call<SignInResponse> signIn(
            @Body SignInRequest signInRequest
    );

    @POST("users/signup")
    Call<SignUpResponse> signUp(
            @Body SignUpRequest signUpRequest
    );

    @POST("post")
    Call<PostRequest> post(
            @Header("AccessToken") String token,
            @Body PostRequest postRequest
    );

    @GET("post/main")
    Call<List<MainResponse>> main(
            @Header("AccessToken") String token
    );

}
