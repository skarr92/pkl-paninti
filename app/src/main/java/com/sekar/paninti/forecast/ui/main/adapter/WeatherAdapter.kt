package com.sekar.paninti.forecast.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sekar.paninti.databinding.ItemWeatherWeekBinding
import com.sekar.paninti.forecast.data.model.Forecastday
import java.text.DateFormat
import java.text.SimpleDateFormat

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherHolder>() {

    class WeatherHolder(private val binding: ItemWeatherWeekBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Forecastday) {
            binding.apply {
                val condition = "${item.day.condition.text}"
                val temp = "${item.day.avgtempC.toInt()}°"
                val day = item.date
                val df: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                val dateFormat: DateFormat = SimpleDateFormat("EEEE")
                val newDay: String = dateFormat.format(df.parse(day))
                val iconWeather = "https:${item.day.condition.icon}"
                tvDay.text = newDay
                tvWeather.text = condition
                tvCel.text = temp
                Glide.with(ivWeather.context)
                    .load(iconWeather)
                    .into(ivWeather)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Forecastday>() {
        override fun areItemsTheSame(
            oldItem: Forecastday,
            newItem: Forecastday
        ) = oldItem.date == newItem.date

        override fun areContentsTheSame(
            oldItem: Forecastday,
            newItem: Forecastday
        ) = oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)

    var items : List<Forecastday>
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
    override fun getItemCount() = items.size
 }