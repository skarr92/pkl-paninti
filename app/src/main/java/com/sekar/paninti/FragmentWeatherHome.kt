package com.sekar.paninti

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sekar.paninti.databinding.FragmentWeatherHomeBinding

class FragmentWeatherHome : Fragment() {

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

//        val listWeatherDay = listOf(
//            WeatherDay(
//                getString(R.string.humidity_percent),
//                R.drawable.ic_cloud,
//                getString(R.string.clock_10)
//            ),
//            WeatherDay(
//                getString(R.string.humidity_percent),
//                R.drawable.ic_two_cloud,
//                getString(R.string.clock_11)
//            ),
//            WeatherDay(
//                getString(R.string.humidity_percent),
//                R.drawable.ic_lightning,
//                getString(R.string.clock_12)
//            ),
//            WeatherDay(
//                getString(R.string.humidity_percent),
//                R.drawable.ic_rain,
//                getString(R.string.clock_13)
//            )
//        )

        binding.rvWeatherDay.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvWeatherDay.setHasFixedSize(true)
        binding.rvWeatherDay.adapter = HomeAdapter()
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