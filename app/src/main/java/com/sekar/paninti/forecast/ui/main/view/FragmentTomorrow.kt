package com.sekar.paninti.forecast.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sekar.paninti.R
import com.sekar.paninti.forecast.ui.main.adapter.WeatherAdapter
import com.sekar.paninti.databinding.FragmentTomorrowBinding
import com.sekar.paninti.forecast.data.api.ApiHelper
import com.sekar.paninti.forecast.data.api.RetrofitBuilder
import com.sekar.paninti.forecast.ui.base.ViewModelFactory
import com.sekar.paninti.forecast.ui.main.viewmodel.MainViewModel
import com.sekar.paninti.forecast.utils.Status.*

class FragmentTomorrow : Fragment() {

    private var adapter: WeatherAdapter = WeatherAdapter()
    private lateinit var binding: FragmentTomorrowBinding
    private lateinit var viewModel: MainViewModel

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
        setCardView()
        setupViewModel()
        setupUI()
        setupObservers()
        backOnClick()
    }

    private fun setCardView(){
        binding.cvGradient.setBackgroundResource(R.drawable.bg_weather_gradient)
    }

    private fun backOnClick(){
        binding.imgBack.setOnClickListener{
            val fragment = FragmentWeatherHome()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.containerWeather,fragment)?.commit()
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[MainViewModel::class.java]
    }

    private fun setupUI(){
        binding.rvWeather.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getForecast().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                showLoading(resource.status == LOADING)
                when (resource.status) {
                    SUCCESS -> {
                        showLoading(false)
                        resource.data?.let { forecast -> adapter.items = forecast.forecast.forecastday }
                        resource.data?.forecast?.forecastday?.component2()?.day.let { weather ->
                            val maxTemp = "${weather?.maxtempC?.toInt()}"
                            val minTemp = "/ ${weather?.mintempC?.toInt()}°"
                            val description = "${weather?.condition?.text}"
                            val humidity = "${weather?.avghumidity}%"
                            val windSpeed = "${weather?.maxwindKph?.toInt()} km/h"
                            val chanceRain = "${weather?.dailyChanceOfRain}%"

                            binding.apply {
                                tvMaxTemp.text = maxTemp
                                tvMinTemp.text = minTemp
                                tvWeatherWeek.text = description
                                tvWindSpeed.text = windSpeed
                                tvHumidityPercent.text = humidity
                                tvChancePercent.text = chanceRain
                            }

                            val animationWeather = binding.animationWeather
                            val codeWeather = weather?.condition?.code
                            when (codeWeather) {
                                1000 -> { animationWeather.setAnimation(R.raw.ic_sunny) }
                                1003, 1006 -> { animationWeather.setAnimation(R.raw.ic_partly_cloudy) }
                                1009, 1030 -> { animationWeather.setAnimation(R.raw.ic_mist) }
                                1066, 1069, 1072, 1210, 1213, 1216 -> { animationWeather.setAnimation(R.raw.ic_snow_sunny) }
                                1114, 1117, 1219, 1222, 1225, 1237, 1255, 1258, 1261, 1264
                                -> { animationWeather.setAnimation(R.raw.ic_snow) }
                                1135, 1147 -> { animationWeather.setAnimation(R.raw.ic_windy) }
                                1063, 1150, 1153, 1168, 1171, 1180, 1183, 1186, 1189,
                                1192, 1195, 1198, 1201, 1204, 1207, 1240, 1243, 1246,
                                1249, 1252 -> { animationWeather.setAnimation(R.raw.ic_partly_shower) }
                                1273, 1276, 1279, 1282 -> { animationWeather.setAnimation(R.raw.ic_storm_showersday) }
                            }
                            animationWeather.playAnimation()
                        }
                    }
                    ERROR -> {
                        Toast.makeText(this.context, it.message, Toast.LENGTH_LONG).show()
                    }
                    LOADING -> {}
                }
            }
        })
    }

    private fun showLoading(loading: Boolean) {
        binding.apply {
            animationLoading.isVisible = loading
            rvWeather.isVisible = !loading
            cvGradient.isVisible = !loading
        }
    }
}