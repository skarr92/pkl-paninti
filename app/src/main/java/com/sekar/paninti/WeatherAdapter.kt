package com.sekar.paninti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherHolder>() {

    inner class WeatherHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvDay = view.findViewById<TextView>(R.id.tvDay)
        val ivWeather = view.findViewById<ImageView>(R.id.imgWeather)
        val tvWeather = view.findViewById<TextView>(R.id.tvWeather)
        val tvCelcius = view.findViewById<TextView>(R.id.tvCel)
    }

    private val differCallback = object : DiffUtil.ItemCallback<WeatherWeek>(){
        override fun areItemsTheSame(oldItem: WeatherWeek, newItem: WeatherWeek): Boolean {
            return oldItem.txtDay == newItem.txtDay
        }

        override fun areContentsTheSame(oldItem: WeatherWeek, newItem: WeatherWeek): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

     override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): WeatherHolder {
         return WeatherHolder(
             LayoutInflater.from(viewGroup.context).inflate(R.layout.item_weather_week, viewGroup, false)
         )
     }

     override fun getItemCount(): Int {
         return differ.currentList.size
     }

     override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
         val weather = differ.currentList[position]

         holder.tvDay.text = weather.txtDay
         holder.ivWeather.setImageResource(weather.imgWeather)
         holder.tvWeather.text = weather.txtWeather
         holder.tvCelcius.text = weather.txtCelcius
     }
 }