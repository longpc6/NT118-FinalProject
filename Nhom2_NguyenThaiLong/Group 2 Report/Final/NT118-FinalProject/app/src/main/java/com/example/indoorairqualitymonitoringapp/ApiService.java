package com.example.indoorairqualitymonitoringapp;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.example.indoorairqualitymonitoringapp.models.DatapointRequest;

public interface ApiService {
    @FormUrlEncoded
    @POST("/auth/realms/master/protocol/openid-connect/token")
    Call<JsonObject> getToken(
            @Field("grant_type") String grantType,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("username") String username,
            @Field("password") String password
    );
    @GET("/api/master/map/js")
    Call<JsonObject> getMapData(@Header("Authorization") String authorization);
    @GET("/api/master/asset/{assetID}")
    Call<JsonObject> getDeviceData(
            @Header("Authorization") String authorization,
            @Path("assetID") String assetID
    );

    @Headers("Content-Type: application/json")
    @POST("/api/master/asset/datapoint/{assetId}/attribute/{attributeName}")
    Call<JsonArray> getDataPoints(
            @Path("assetId") String assetId,
            @Path("attributeName") String attributeName,
            @Header("Authorization") String authorization,
            @Body DatapointRequest request
    );

    @GET("data/2.5/forecast")
    Call<WeatherForecast> getWeatherForecast(
            @Query("q") String city,
            @Query("appid") String apiKey,
            @Query("units") String units
    );
}
