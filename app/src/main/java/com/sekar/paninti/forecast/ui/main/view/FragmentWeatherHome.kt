package com.sekar.paninti.forecast.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.sekar.paninti.R
import com.sekar.paninti.databinding.FragmentWeatherHomeBinding
import com.sekar.paninti.forecast.data.api.ApiHelper
import com.sekar.paninti.forecast.data.api.RetrofitBuilder
import com.sekar.paninti.forecast.data.model.Current
import com.sekar.paninti.forecast.data.model.Hour
import com.sekar.paninti.forecast.ui.base.ViewModelFactory
import com.sekar.paninti.forecast.ui.main.adapter.HomeAdapter
import com.sekar.paninti.forecast.ui.main.viewmodel.MainViewModel
import com.sekar.paninti.forecast.utils.Status
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class FragmentWeatherHome : Fragment() {

    private lateinit var viewModel : MainViewModel
    private lateinit var adapter : HomeAdapter
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
        binding.rvWeatherDay.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        adapter = HomeAdapter()
        binding.rvWeatherDay.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getForecast().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        visibilitySuccessError()
                        resource.data?.let { forecast -> retrieveList(forecast.forecast.forecastday.component1().hour) }

                        val location = resource.data?.location?.name
                        val temp = "${resource.data?.current?.tempC?.toInt()}°"
                        val condition = resource.data?.current?.condition?.text
                        val windSpeed = "${resource.data?.current?.windKph?.toInt()} km/h"
                        val humidityPercent = "${resource.data?.current?.humidity}%"
                        val chanceRain = "${resource.data?.forecast?.forecastday?.component1()?.day?.dailyChanceOfRain}%"
                        val date = resource.data?.forecast?.forecastday?.component1()?.date
                        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                        val dateFormat: DateFormat = SimpleDateFormat("EEEE, dd MMMM")
                        val newDate: String = dateFormat.format(df.parse(date))

                        binding.tvLocation.text = location
                        binding.tvCelcius.text = temp
                        binding.tvWeather.text = condition
                        binding.tvDate.text = newDate
                        binding.tvWindSpeed.text = windSpeed
                        binding.tvHumidityPercent.text = humidityPercent
                        binding.tvChancePercent.text = chanceRain

                        val codeWeather = resource.data?.current?.condition?.code
                        val animationWeather = binding.animationWeather
                        val time = resource.data?.current?.lastUpdated?.substring(11, 13)?.toInt()
                        var timeCondition = ""

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

                    Status.ERROR -> {
                        visibilitySuccessError()
                        Toast.makeText(this.context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        visibilityLoading()
                    }
                }
            }
        })
    }

    private fun retrieveList(forecast: List<Hour>) {
        adapter.apply {
            this.items = forecast
        }
    }

    private fun visibilitySuccessError() {
        binding.rvWeatherDay.visibility = View.VISIBLE
        binding.tvToday.visibility = View.VISIBLE
        binding.tvNextSevenDays.visibility = View.VISIBLE
        binding.cvGradient.visibility = View.VISIBLE
        binding.animationLoading.visibility = View.GONE
    }

    private fun visibilityLoading(){
        binding.rvWeatherDay.visibility = View.GONE
        binding.tvToday.visibility = View.GONE
        binding.tvNextSevenDays.visibility = View.GONE
        binding.cvGradient.visibility = View.GONE
        binding.animationLoading.visibility = View.VISIBLE
    }

    private fun nextSevenDay(){
        binding.tvNextSevenDays.setOnClickListener{
            val fragment = FragmentTomorrow()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.containerWeather,fragment)?.commit()
        }
    }
}