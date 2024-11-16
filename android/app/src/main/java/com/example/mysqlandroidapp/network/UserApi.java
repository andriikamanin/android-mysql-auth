package com.example.mysqlandroidapp.network;

import com.example.mysqlandroidapp.models.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserApi {

    @FormUrlEncoded
    @POST("register.php")
    Call<UserResponse> registerUser(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<UserResponse> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );


}
