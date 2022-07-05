package com.tmdhoon.todolist.Api;

import com.tmdhoon.todolist.SignIn.SignInRequest;
import com.tmdhoon.todolist.SignIn.SignInResponse;
import com.tmdhoon.todolist.SignUp.SignUpRequest;
import com.tmdhoon.todolist.SignUp.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServerApi {

    @POST("/users/signin")
    Call<SignInResponse> Signin(
            @Body SignInRequest signInRequest
    );

    @POST("/users/signup")
    Call<SignUpResponse> Signup(
            @Body SignUpRequest signUpRequest
    );

}
