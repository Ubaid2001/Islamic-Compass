package com.example.islamiccompass.api;

import com.example.islamiccompass.helper.GeoResponseObject;
import com.example.islamiccompass.helper.HadithResponseObject;
import com.example.islamiccompass.helper.SalahResponseObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RandomApi {

    @GET("/bukhari")
    Call<HadithResponseObject> getHadith();

    @GET("{date}")
    Call<SalahResponseObject> getSalah(@Path("date") String date, @Query("latitude") double latitude, @Query("longitude") double longitude);

    @GET("/reverse")
    Call<GeoResponseObject> getGeocode(@Query("lat") double latitude, @Query("lon") double longitude, @Query("api_key") String apiKey);
}
