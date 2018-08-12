package com.pheramor.registerationapp.retrofit;

import com.pheramor.registerationapp.retrofit.models.User;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface UserQuery {
    @Multipart
    @POST
    Call<Void> postUser(@Part MultipartBody.Part image, @QueryMap Map<String, String> queryParams,
                        @Url String url);
}
