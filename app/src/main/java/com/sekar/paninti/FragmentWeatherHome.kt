package com.sekar.paninti

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.TypedArrayUtils.getText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sekar.paninti.databinding.FragmentWeatherHomeBinding

class FragmentWeatherHome : Fragment() {

    private val HomeAdpt by lazy { HomeAdapter() }
    private lateinit var binding: FragmentWeatherHomeBinding

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
        view()
    }

    private fun view(){

        cardView()
        recyclerViewDay()
        nextSevenDay()
    }

    private fun cardView(){
        binding.cvGradient.setBackgroundResource(R.drawable.bg_weather_gradient)
    }


    private fun recyclerViewDay(){

        HomeAdpt.differ.submitList(loadData())
        binding.apply {
            binding.rvWeatherDay.apply {
                layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = HomeAdpt
            }
        }
    }

    fun loadData(): MutableList<WeatherDay>{
        val data : MutableList<WeatherDay> = mutableListOf()

            data.add(WeatherDay(getString(R.string.humidity_percent), R.drawable.ic_cloud,getString(R.string.clock_10)))
            data.add(WeatherDay(getString(R.string.humidity_percent), R.drawable.ic_cloud,getString(R.string.clock_11)))
            data.add(WeatherDay(getString(R.string.humidity_percent), R.drawable.ic_cloud,getString(R.string.clock_12)))
            data.add(WeatherDay(getString(R.string.humidity_percent), R.drawable.ic_cloud,getString(R.string.clock_13)))
        return data
    }

    private fun nextSevenDay(){
        binding.tvNextSevenDays.setOnClickListener{
           tomorrow()
        }
    }

    private fun tomorrow(){
        val fragment = FragmentTomorrow()
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.containerWeather,fragment)?.commit()
    }
}