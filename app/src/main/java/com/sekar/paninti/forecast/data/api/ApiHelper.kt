package com.sekar.paninti.forecast.data.api

class ApiHelper(private val apiService: ApiService) {

    companion object{
        private const val API_KEY = "eae09993ff7a74db3f2636e82a313a77"
    }

    suspend fun getForecast() = apiService.getForecast("cimahi", API_KEY, "metric")
}