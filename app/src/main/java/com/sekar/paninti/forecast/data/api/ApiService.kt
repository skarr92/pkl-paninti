package com.sekar.paninti.forecast.data.api

import com.sekar.paninti.forecast.data.model.Weather
import com.sekar.paninti.forecast.utils.EndPoint
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(EndPoint.WEATHER_PATH)
    suspend fun getForecast(
        @Query("key") apiKey : String,
        @Query("q") cityName : String,
        @Query("days") days : String,
        @Query("aqi") aqi : String,
        @Query("alerts") alerts : String,
    ) : Weather

}