package com.example.chargingmap;

import com.example.chargingmap.models.ChargePoint;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenchargeMapClient {

    @GET("?output=json")
    Call<List<ChargePoint>> list(
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            @Query("distance") int distance);

    // Url format
    // https://api.openchargemap.io/v3/poi/?output=json&latitude={lat}&longitude={long}&distance=5
    // X-API-Key: 123
    public static OpenchargeMapClient build(String key) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(chain -> {
            Request request = chain.request().newBuilder().addHeader("X-API-Key", key).build();
            return chain.proceed(request);
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openchargemap.io/v3/poi/")
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(OpenchargeMapClient.class);
    }
}
