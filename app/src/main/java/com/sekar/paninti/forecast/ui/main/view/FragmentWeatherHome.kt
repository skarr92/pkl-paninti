package com.sekar.paninti.forecast.ui.main.view

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sekar.paninti.forecast.ui.main.adapter.HomeAdapter
import com.sekar.paninti.R
import com.sekar.paninti.databinding.FragmentWeatherHomeBinding
import com.sekar.paninti.forecast.data.api.ApiHelper
import com.sekar.paninti.forecast.data.api.RetrofitBuilder
import com.sekar.paninti.forecast.data.model.WeatherData
import com.sekar.paninti.forecast.ui.base.ViewModelFactory
import com.sekar.paninti.forecast.ui.main.adapter.WeatherAdapter
import com.sekar.paninti.forecast.ui.main.viewmodel.MainViewModel
import com.sekar.paninti.forecast.utils.Status

class FragmentWeatherHome : Fragment() {

    private lateinit var viewModel : MainViewModel
    private lateinit var adapter : HomeAdapter
    private lateinit var binding : FragmentWeatherHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherHomeBinding.inflate(layoutInflater)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCardView()
        setupViewModel()
        setupUI()
        setupObservers()
        nextSevenDay()
    }

    private fun setCardView(){
        binding.cvGradient.setBackgroundResource(R.drawable.bg_weather_gradient)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[MainViewModel::class.java]
    }

    private fun setupUI(){
        binding.rvWeatherDay.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        adapter = HomeAdapter()
        binding.rvWeatherDay.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getForecast().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.rvWeatherDay.visibility = View.VISIBLE
                        resource.data?.let { forecast -> retrieveList(forecast.list) }
                    }
                    Status.ERROR -> {
                        binding.rvWeatherDay.visibility = View.VISIBLE
                        Toast.makeText(this.context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.rvWeatherDay.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(forecast: List<WeatherData>) {
        adapter.apply {
            this.items = forecast
        }
    }

    private fun nextSevenDay(){
        binding.tvNextSevenDays.setOnClickListener{
            val fragment = FragmentTomorrow()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.containerWeather,fragment)?.commit()
        }
    }
}