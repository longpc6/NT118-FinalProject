package com.example.indoorairqualitymonitoringapp;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("/auth/realms/master/protocol/openid-connect/token")
    Call<JsonObject> getToken(
            @Field("grant_type") String grantType,  // Phương thức cấp quyền, ví dụ "password"
            @Field("client_id") String clientId,    // ID của ứng dụng của bạn
            @Field("client_secret") String clientSecret, // Mật khẩu ứng dụng của bạn (nếu có)
            @Field("username") String username,      // Tên người dùng
            @Field("password") String password       // Mật khẩu người dùng
    );
}
