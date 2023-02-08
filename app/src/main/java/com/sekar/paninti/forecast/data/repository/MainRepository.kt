package com.sekar.paninti.forecast.data.repository

import com.sekar.paninti.forecast.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getForecast() = apiHelper.getForecast()
}