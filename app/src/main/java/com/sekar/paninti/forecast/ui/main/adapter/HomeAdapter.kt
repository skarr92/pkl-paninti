package com.sekar.paninti.forecast.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sekar.paninti.R
import com.sekar.paninti.databinding.ItemWeatherDayBinding
import com.sekar.paninti.forecast.data.model.Current
import com.sekar.paninti.forecast.data.model.Hour
import com.sekar.paninti.forecast.ui.main.view.FragmentWeatherHome

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeHolder>() {

    private val limit = 4

    class HomeHolder(private val binding: ItemWeatherDayBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Hour) {
            binding.apply {
                val humidity = "${item.humidity}%"
                val hour = "${item.time.substring(11, 16)}"
                val iconWeather = "https:${item.condition.icon}"


                tvHmdty.text = humidity
                tvHour.text = hour
                Glide.with(ivWeatherDay.context)
                    .load(iconWeather)
                    .into(ivWeatherDay)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Hour>() {
        override fun areItemsTheSame(oldItem: Hour, newItem: Hour): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: Hour, newItem: Hour): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    var items : List<Hour>
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
        return items.size
//        return if (items.size > limit) limit else items.size
    }
}