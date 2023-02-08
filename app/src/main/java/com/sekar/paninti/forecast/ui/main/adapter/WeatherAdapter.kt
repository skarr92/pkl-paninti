package com.sekar.paninti.forecast.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sekar.paninti.R
import com.sekar.paninti.databinding.ItemWeatherDayBinding
import com.sekar.paninti.databinding.ItemWeatherWeekBinding
import com.sekar.paninti.forecast.data.model.WeatherData

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherHolder>() {

    private val limit = 7

    class WeatherHolder(private val binding: ItemWeatherWeekBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weather: WeatherData) {
            binding.apply {
                val day = "Monday"
                val main = "${weather.weather.component1().main}"
                val temp = "${weather.main.temp.toInt()}°"

                if(main == "Clouds") {
                    ivWeather.setImageResource(R.drawable.ic_cloud)
                    tvWeather.text = "Cloudy"
                } else if (main == "Rain") {
                    ivWeather.setImageResource(R.drawable.ic_rain)
                    tvWeather.text = "Rainy"
                } else if (main == "Clear") {
                    ivWeather.setImageResource(R.drawable.ic_sun)
                    tvWeather.text = "Sunny"
                } else {
                    ivWeather.setImageResource(R.drawable.ic_cloud)
                    tvWeather.text = "Cloudy"
                }

                tvDay.text = day
                tvCel.text = temp
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<WeatherData>() {
        override fun areItemsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem.dtTxt.substring(0, 10) == newItem.dtTxt.substring(0, 10)
        }

        override fun areContentsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    var items : List<WeatherData>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WeatherHolder(
        ItemWeatherWeekBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        holder.bind(items[position])
        holder.setIsRecyclable(true)
    }
    override fun getItemCount(): Int {
        return if (items.size > limit) limit else items.size
    }
 }