package com.tmdhoon.todolist.Api;

import com.tmdhoon.todolist.Request.EditRequest;
import com.tmdhoon.todolist.Request.PostRequest;
import com.tmdhoon.todolist.Request.SignInRequest;
import com.tmdhoon.todolist.Response.MainResponse;
import com.tmdhoon.todolist.Response.MyResponse;
import com.tmdhoon.todolist.Response.SignInResponse;
import com.tmdhoon.todolist.Request.SignUpRequest;
import com.tmdhoon.todolist.Response.SignUpResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    @GET("users/my")
    Call<MyResponse> my(
            @Header("AccessToken") String token
    );

    @GET("post/main/like/{todoId}")
    Call<Void> like(
            @Header("AccessToken") String token,
            @Path("todoId") long todoId
    );

    @PUT("users/my/{todoId}")
    Call<Void> edit(
            @Header("AccessToken") String token,
            @Path("todoId") long todoId,
            @Body EditRequest editRequest
    );

    @DELETE("users/my/{todoId}")
    Call<Void> delete(
            @Header("AccessToken") String token,
            @Path("todoId") long todoId
    );

    @PATCH("users/my/{todoId}")
    Call<Void> success(
            @Header("AccessToken") String token,
            @Path("todoId") long todoId
    );


}
