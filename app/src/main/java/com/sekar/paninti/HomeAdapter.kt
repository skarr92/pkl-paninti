package com.sekar.paninti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sekar.paninti.databinding.FragmentWeatherHomeBinding
import com.sekar.paninti.databinding.ItemWeatherDayBinding

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeHolder>() {
    private lateinit var binding: ItemWeatherDayBinding

    inner class HomeHolder : RecyclerView.ViewHolder(binding.root) {
        fun setData(item: WeatherDay) {
            binding.apply {
                binding.tvHumidity.text = item.txtHumidity
                binding.ivWeatherDay.setImageResource(item.imgWeatherDay)
                binding.tvHour.text = item.txtHour
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<WeatherDay>() {
        override fun areItemsTheSame(oldItem: WeatherDay, newItem: WeatherDay): Boolean {
            return oldItem.txtHour == newItem.txtHour
        }

        override fun areContentsTheSame(oldItem: WeatherDay, newItem: WeatherDay): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): HomeHolder {
        binding = ItemWeatherDayBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return HomeHolder()
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(true)
    }

}