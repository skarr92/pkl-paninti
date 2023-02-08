package com.sekar.paninti.forecast.data.model

import com.google.gson.annotations.SerializedName

data class WeatherMain(
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String
)