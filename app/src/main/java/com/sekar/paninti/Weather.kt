package com.sekar.paninti

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Weather : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        supportFragmentManager.beginTransaction().replace(R.id.containerWeather, FragmentWeatherHome()).commit()
    }
}