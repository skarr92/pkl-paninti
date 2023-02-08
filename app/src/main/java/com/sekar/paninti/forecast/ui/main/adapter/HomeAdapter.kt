package com.sekar.paninti.forecast.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sekar.paninti.R
import com.sekar.paninti.databinding.ItemWeatherDayBinding
import com.sekar.paninti.forecast.data.model.WeatherData

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeHolder>() {

    private val limit = 4

    class HomeHolder(private val binding: ItemWeatherDayBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weather: WeatherData) {
            binding.apply {
                val humidity = "${weather.main.humidity}%"
                val hour = "${weather.dtTxt.substring(11, 16)}"
                val main = "${weather.weather.component1().main}"

                if(main == "Clouds") ivWeatherDay.setImageResource(R.drawable.ic_two_cloud)
                else if (main == "Rain") ivWeatherDay.setImageResource(R.drawable.ic_rain)
                else if (main == "Clear") ivWeatherDay.setImageResource(R.drawable.ic_sun)
                else ivWeatherDay.setImageResource(R.drawable.ic_cloud)
                tvHmdty.text = humidity
                tvHour.text = hour
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<WeatherData>() {
        override fun areItemsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem.dtTxt == newItem.dtTxt
        }

        override fun areContentsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    var items : List<WeatherData>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomeHolder(
        ItemWeatherDayBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        holder.bind(items[position])
        holder.setIsRecyclable(true)
    }
    override fun getItemCount(): Int {
        return if (items.size > limit) limit else items.size
    }
}