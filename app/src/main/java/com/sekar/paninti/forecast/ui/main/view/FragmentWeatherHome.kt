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
import androidx.recyclerview.widget.LinearLayoutManager
import com.sekar.paninti.R
import com.sekar.paninti.databinding.FragmentWeatherHomeBinding
import com.sekar.paninti.forecast.data.api.ApiHelper
import com.sekar.paninti.forecast.data.api.RetrofitBuilder
import com.sekar.paninti.forecast.ui.base.ViewModelFactory
import com.sekar.paninti.forecast.ui.main.adapter.HomeAdapter
import com.sekar.paninti.forecast.ui.main.viewmodel.MainViewModel
import com.sekar.paninti.forecast.utils.Status.*
import java.text.DateFormat
import java.text.SimpleDateFormat

class FragmentWeatherHome : Fragment() {

    private var adapter : HomeAdapter = HomeAdapter()
    private lateinit var viewModel : MainViewModel
    private lateinit var binding : FragmentWeatherHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherHomeBinding.inflate(layoutInflater)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCardView()
        setupViewModel()
        setupUI()
        setupObservers()
        nextSevenDay()
    }

    private fun setCardView(){
        binding.cvGradient.setBackgroundResource(R.drawable.bg_weather_gradient)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[MainViewModel::class.java]
        viewModel.getForecast()
    }

    private fun setupUI(){
        binding.rvWeatherDay.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getForecast().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                showLoading(resource.status == SUCCESS || resource.status == ERROR)
                showLoading(resource.status == LOADING)
                when (resource.status) {
                    SUCCESS -> {
                        resource.data?.let { forecast -> adapter.items = forecast.forecast.forecastday.component1().hour }
                        resource.data?.current.let { weather ->
                            val temp = "${weather?.tempC?.toInt()}°"
                            val condition = weather?.condition?.text
                            val windSpeed = "${weather?.windKph?.toInt()} km/h"
                            val humidityPercent = "${weather?.humidity}%"
                            val codeWeather = weather?.condition?.code
                            val time = weather?.lastUpdated?.substring(11, 13)?.toInt()
                            val animationWeather = binding.animationWeather
                            var timeCondition = ""

                            binding.apply {
                                tvWindSpeed.text = windSpeed
                                tvHumidityPercent.text = humidityPercent
                                tvCelcius.text = temp
                                tvWeather.text = condition
                            }

                            when (time){
                                in 6..18-> timeCondition = "Day"
                                in 19..23, in 0..5 -> timeCondition = "Night"
                            }

                            if (timeCondition == "Day"){
                                when (codeWeather) {
                                    1000 -> {animationWeather.setAnimation(R.raw.ic_sunny)}
                                    1003, 1006 -> {animationWeather.setAnimation(R.raw.ic_partly_cloudy)}
                                    1009, 1030 -> {animationWeather.setAnimation(R.raw.ic_mist)}
                                    1066, 1069, 1072, 1210, 1213, 1216 -> {animationWeather.setAnimation(R.raw.ic_snow_sunny)}
                                    1114, 1117, 1219, 1222, 1225, 1237, 1255, 1258, 1261, 1264
                                    -> {animationWeather.setAnimation(R.raw.ic_snow)}
                                    1135, 1147 -> {animationWeather.setAnimation(R.raw.ic_windy)}
                                    1063, 1150, 1153, 1168, 1171, 1180, 1183, 1186, 1189,
                                    1192, 1195, 1198, 1201, 1204, 1207, 1240, 1243, 1246,
                                    1249, 1252 -> {animationWeather.setAnimation(R.raw.ic_partly_shower)}
                                    1273, 1276, 1279, 1282 -> {animationWeather.setAnimation(R.raw.ic_storm_showersday)}
                                }
                                animationWeather.playAnimation()
                            } else if (timeCondition == "Night"){
                                when (codeWeather) {
                                    1000 -> {animationWeather.setAnimation(R.raw.ic_night)}
                                    1003, 1006 -> {animationWeather.setAnimation(R.raw.ic_cloudy_night)}
                                    1009, 1030 -> {animationWeather.setAnimation(R.raw.ic_mist)}
                                    1066, 1069, 1072, 1210, 1213, 1216 -> {animationWeather.setAnimation(R.raw.ic_snow_night)}
                                    1114, 1117, 1219, 1222, 1225, 1237, 1255, 1258, 1261, 1264
                                    -> {animationWeather.setAnimation(R.raw.ic_snow)}
                                    1135, 1147 -> {animationWeather.setAnimation(R.raw.ic_windy)}
                                    1063, 1150, 1153, 1168, 1171, 1180, 1183, 1186, 1189,
                                    1192, 1195, 1198, 1201, 1204, 1207, 1240, 1243, 1246,
                                    1249, 1252-> {animationWeather.setAnimation(R.raw.ic_rainy_night)}
                                    1273, 1276, 1279, 1282 -> {animationWeather.setAnimation(R.raw.ic_storm)}
                                }
                                animationWeather.playAnimation()
                            }
                        }

                        val location = resource.data?.location?.name
                        val chanceRain = "${resource.data?.forecast?.forecastday?.component1()?.day?.dailyChanceOfRain}%"
                        val date = resource.data?.forecast?.forecastday?.component1()?.date
                        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                        val dateFormat: DateFormat = SimpleDateFormat("EEEE, dd MMMM")
                        val newDate: String = dateFormat.format(df.parse(date))
                        binding.apply {
                            tvLocation.text = location
                            tvDate.text = newDate
                            tvChancePercent.text = chanceRain
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
            groupHome.isVisible = !loading
            cvGradient.isVisible = !loading
        }
    }

    private fun nextSevenDay(){
        binding.tvNextSevenDays.setOnClickListener{
            val fragment = FragmentTomorrow()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.containerWeather,fragment)?.commit()
        }
    }
}