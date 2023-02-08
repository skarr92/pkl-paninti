package com.sekar.paninti.forecast.data.model

import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("3h")
    val h: Double
)