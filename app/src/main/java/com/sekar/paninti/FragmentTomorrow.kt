package com.sekar.paninti

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sekar.paninti.databinding.FragmentTomorrowBinding

class FragmentTomorrow : Fragment() {

    private lateinit var binding: FragmentTomorrowBinding
    private val WeatherAdpt by lazy { WeatherAdapter() }

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
        backOnClick()
    }

    private fun cardView(){
        binding.cvGradient.setBackgroundResource(R.drawable.bg_weather_gradient)
    }

    private fun recyclerViewWeather(){

        WeatherAdpt.differ.submitList(loadData())
        binding.apply {
            binding.rvWeather.apply {
                layoutManager = LinearLayoutManager(this.context)
                adapter = WeatherAdpt
            }
        }
    }

    fun loadData(): MutableList<WeatherWeek>{
        val data : MutableList<WeatherWeek> = mutableListOf()

        data.add(WeatherWeek(getString(R.string.monday), R.drawable.ic_cloud, getString(R.string.cloudy), getString(R.string.celcius_29)))
        data.add(WeatherWeek(getString(R.string.tuesday), R.drawable.ic_cloud, getString(R.string.cloudy), getString(R.string.celcius_24)))
        data.add(WeatherWeek(getString(R.string.wednesday), R.drawable.ic_rain, getString(R.string.rainy), getString(R.string.celcius_17)))
        data.add(WeatherWeek(getString(R.string.thursday), R.drawable.ic_lightning, getString(R.string.storm), getString(R.string.celcius_18)))
        data.add(WeatherWeek(getString(R.string.friday), R.drawable.ic_sun, getString(R.string.sunny), getString(R.string.celcius_22)))
        data.add(WeatherWeek(getString(R.string.saturday), R.drawable.ic_sun, getString(R.string.sunny), getString(R.string.celcius_24)))
        data.add(WeatherWeek(getString(R.string.sunday), R.drawable.ic_sun, getString(R.string.sunny), getString(R.string.celcius_30)))
        return data
    }

    private fun backOnClick(){
        binding.imgBack.setOnClickListener{
            back()
        }
    }

    private fun back(){
        val fragment = FragmentWeatherHome()
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.containerWeather,fragment)?.commit()
    }

}