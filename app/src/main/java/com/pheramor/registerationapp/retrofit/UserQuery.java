package com.pheramor.registerationapp.retrofit;

import com.pheramor.registerationapp.retrofit.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserQuery {
    @POST()
    Call<Void> postUser(@Body User user);
}
