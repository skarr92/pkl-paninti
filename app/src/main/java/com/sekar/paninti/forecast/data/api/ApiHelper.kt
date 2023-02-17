package com.sekar.paninti.forecast.data.api

class ApiHelper(private val apiService: ApiService) {

    companion object{
        private const val API_KEY = "083671bf964b43dea0e81000230902"
    }

    suspend fun getForecast() = apiService.getForecast(API_KEY, "Cimahi", "7", "no", "no")
}