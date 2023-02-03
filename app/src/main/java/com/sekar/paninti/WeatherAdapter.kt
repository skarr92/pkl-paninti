package com.sekar.paninti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sekar.paninti.databinding.ItemWeatherDayBinding
import com.sekar.paninti.databinding.ItemWeatherWeekBinding

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherHolder>() {
    private lateinit var binding : ItemWeatherWeekBinding

    inner class WeatherHolder : RecyclerView.ViewHolder(binding.root) {
        fun setData(item: WeatherWeek) {
            binding.apply {
                binding.tvDay.text = item.txtDay
                binding.imgWeather.setImageResource(item.imgWeather)
                binding.tvWeather.text = item.txtWeather
                binding.tvCel.text = item.txtCelcius
            }
        }
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
         binding = ItemWeatherWeekBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
         return WeatherHolder()
     }

     override fun getItemCount(): Int {
         return differ.currentList.size
     }

     override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
         holder.setData(differ.currentList[position])
         holder.setIsRecyclable(true)
     }
 }