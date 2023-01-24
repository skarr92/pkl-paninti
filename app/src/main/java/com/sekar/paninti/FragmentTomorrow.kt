package com.sekar.paninti

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sekar.paninti.databinding.FragmentTomorrowBinding

class FragmentTomorrow : Fragment() {

    private lateinit var binding: FragmentTomorrowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTomorrowBinding.inflate(layoutInflater)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view()
    }

    private fun view(){
        cardView()
    }

    private fun cardView(){
        binding.cvGradient.setBackgroundResource(R.drawable.bg_weather_gradient)
    }
}