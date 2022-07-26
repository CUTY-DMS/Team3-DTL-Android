package com.tmdhoon.todolist.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiProvider {                                                                          // ApiProvider 클래스
    private static Retrofit retrofit;                                                               // 레트로핏
    private static String BASE_URL = "http://13.125.180.241:8080";                                  // 베이스 URL

    public static Retrofit getInstance() {                                                          // 레트로핏을 만드는 메서드
        if(retrofit==null){                                                                         // 레트로핏이 없는 경우
            retrofit = new Retrofit.Builder()                                                       // 새 레트로핏 빌드
                    .baseUrl(BASE_URL)                                                              // 베이스 URL
                    .addConverterFactory(GsonConverterFactory.create())                             // GsonConverterFactory를 만듦
                    .build();                                                                       // 빌드
        }
        return retrofit;
    }
}
