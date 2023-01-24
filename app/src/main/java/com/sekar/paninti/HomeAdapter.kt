package com.sekar.paninti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HomeAdapter (private val context: FragmentWeatherHome, private val day: List<WeatherDay>) : RecyclerView.Adapter<HomeHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): HomeHolder {
        return HomeHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_weather_day, viewGroup, false)
        )
    }

    override fun getItemCount(): Int = day.size

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        holder.bindHome(day[position])
    }
}


class HomeHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val tvHumidity = view.findViewById<TextView>(R.id.tvHumidity)
    private val ivWeather = view.findViewById<ImageView>(R.id.imgWeatherDay)
    private val tvHour = view.findViewById<TextView>(R.id.tvHour)

    fun bindHome(day: WeatherDay) {
        tvHumidity.text = day.txtHumidity
        ivWeather.setImageResource(day.imgWeatherDay)
        tvHour.text = day.txtHour
    }
}