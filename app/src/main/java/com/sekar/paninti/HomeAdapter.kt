package com.sekar.paninti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeHolder>() {

    inner class HomeHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvHumidity = view.findViewById<TextView>(R.id.tvHumidity)
        val ivWeather = view.findViewById<ImageView>(R.id.imgWeatherDay)
        val tvHour = view.findViewById<TextView>(R.id.tvHour)
    }

    private val differCallback = object : DiffUtil.ItemCallback<WeatherDay>(){
        override fun areItemsTheSame(oldItem: WeatherDay, newItem: WeatherDay): Boolean {
            return oldItem.txtHour == newItem.txtHour
        }

        override fun areContentsTheSame(oldItem: WeatherDay, newItem: WeatherDay): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): HomeHolder {
        return HomeHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_weather_day, viewGroup, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        val day = differ.currentList[position]

        holder.tvHumidity.text = day.txtHumidity
        holder.ivWeather.setImageResource(day.imgWeatherDay)
        holder.tvHour.text = day.txtHour
    }

}