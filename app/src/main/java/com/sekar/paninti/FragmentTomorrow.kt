package com.sekar.paninti

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sekar.paninti.databinding.FragmentTomorrowBinding

class FragmentTomorrow : Fragment() {

    private lateinit var binding: FragmentTomorrowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTomorrowBinding.inflate(layoutInflater)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view()
    }

    private fun view(){
        cardView()
        recyclerViewWeather()
    }

    private fun cardView(){
        binding.cvGradient.setBackgroundResource(R.drawable.bg_weather_gradient)
    }

    private fun recyclerViewWeather(){

        val listWeatherWeek = listOf(
            WeatherWeek(
                "Monday",
                R.drawable.ic_cloud,
                "Cloudy",
                "29°"
            ),
            WeatherWeek(
                "Tuesday",
                R.drawable.ic_cloud,
                "Cloudy",
                "24°"
            ),
            WeatherWeek(
                "Wednesday",
                R.drawable.ic_rain,
                "Rainy",
                "17°"
            ),
            WeatherWeek(
                "Thursday",
                R.drawable.ic_lightning,
                "Storm",
                "18°"
            ),
            WeatherWeek(
                "Friday",
                R.drawable.ic_sun,
                "Sunny",
                "22°"
            ),
            WeatherWeek(
                "Saturday",
                R.drawable.ic_sun,
                "Sunny",
                "24°"
            ),
            WeatherWeek(
                "Sunday",
                R.drawable.ic_sun,
                "Sunny",
                "30°"
            ),
        )

        val recyclerView = binding.rvWeather
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = WeatherAdapter(this, listWeatherWeek)

    }

}