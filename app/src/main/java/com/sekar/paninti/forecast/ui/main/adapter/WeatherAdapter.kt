package com.sekar.paninti.forecast.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sekar.paninti.databinding.ItemWeatherWeekBinding
import com.sekar.paninti.forecast.data.model.Forecastday
import com.sekar.paninti.forecast.ui.main.viewmodel.MainViewModel
import com.sekar.paninti.forecast.utils.UnitPreference
import java.text.DateFormat
import java.text.SimpleDateFormat

class WeatherAdapter( private val viewModel: MainViewModel) : RecyclerView.Adapter<WeatherAdapter.WeatherHolder>() {

    inner class WeatherHolder(private val binding: ItemWeatherWeekBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Forecastday?) {
            binding.apply {

                val day = item?.date
                val df: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                val dateFormat: DateFormat = SimpleDateFormat("EEEE")
                val newDay: String = dateFormat.format(df.parse(day))
                val condition = "${item?.day?.condition?.text}"
                val iconWeather = "https:${item?.day?.condition?.icon}"

                val unitPreference = viewModel.unitPreference.value
                val temperature = when (unitPreference) {
                    UnitPreference.CELSIUS -> item?.day?.avgtempC?.toInt()
                    UnitPreference.FAHRENHEIT -> item?.day?.avgtempF?.toInt()
                    UnitPreference.KELVIN -> item?.day?.avgtempC?.toInt()?.plus(273)
                    else -> {item?.day?.avgtempC?.toInt()}
                }
                val temperatureText = when (unitPreference) {
                    UnitPreference.CELSIUS -> "$temperature°C"
                    UnitPreference.FAHRENHEIT -> "$temperature°F"
                    UnitPreference.KELVIN -> "$temperature K"
                    else -> {"$temperature°"}
                }

                tvDay.text = newDay
                tvWeather.text = condition
                tvCel.text = temperatureText
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