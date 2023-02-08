package com.sekar.paninti.forecast.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sekar.paninti.R

class Weather : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        supportFragmentManager.beginTransaction().replace(R.id.containerWeather, FragmentWeatherHome()).commit()
    }
}