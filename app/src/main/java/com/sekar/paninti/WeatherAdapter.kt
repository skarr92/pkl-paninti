package com.sekar.paninti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

 class WeatherAdapter(private val context: FragmentTomorrow, private val weather: List<WeatherWeek>) : RecyclerView.Adapter<WeatherHolder>() {

     override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): WeatherHolder {
         return WeatherHolder(
             LayoutInflater.from(viewGroup.context)
                 .inflate(R.layout.item_weather_week, viewGroup, false)
         )
     }

     override fun getItemCount(): Int = weather.size

     override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
         holder.bindWeather(weather[position])
     }
 }

    class WeatherHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvDay = view.findViewById<TextView>(R.id.tvDay)
        private val ivWeather = view.findViewById<ImageView>(R.id.imgWeather)
        private val tvWeather = view.findViewById<TextView>(R.id.tvWeather)
        private val tvCelcius = view.findViewById<TextView>(R.id.tvCel)

        fun bindWeather(weather: WeatherWeek) {
            tvDay.text = weather.txtDay
            ivWeather.setImageResource(weather.imgWeather)
            tvWeather.text = weather.txtWeather
            tvCelcius.text = weather.txtCelcius
        }
    }