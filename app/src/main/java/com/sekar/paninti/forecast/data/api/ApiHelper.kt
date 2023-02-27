package com.sekar.paninti.forecast.data.api

class ApiHelper(private val apiService: ApiService) {

    companion object{
        private const val API_KEY = "a3fe2cd98b5b4050a1711726232302"
    }

    suspend fun getForecast() = apiService.getForecast(API_KEY, "Cimahi", "7", "no", "no")
}