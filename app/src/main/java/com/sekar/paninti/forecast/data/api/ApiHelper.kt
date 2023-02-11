package com.sekar.paninti.forecast.data.api

class ApiHelper(private val apiService: ApiService) {

    companion object{
        private const val API_KEY = "6610b475e8ba493a93444335230302"
    }

    suspend fun getForecast() = apiService.getForecast(API_KEY, "Cimahi", "7", "no", "no")
}