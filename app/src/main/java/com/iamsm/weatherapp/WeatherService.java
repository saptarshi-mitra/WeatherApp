package com.iamsm.weatherapp;


import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.Query;

public interface WeatherService {

    @GET("data/2.5/forecast?")
    Call<WeatherResponse> getTemp(@Query("id") int id, @Query("APPID") String appid);

    @GET("data/2.5/forecast?")
    Call<WeatherResponse> getTemp_entry(@Query("q") String q, @Query("APPID") String appid);

}
