package com.weatherapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.weatherapp.databinding.FragmentDaysBinding
import com.weatherapp.domain.common.NetworkResult
import com.weatherapp.domain.common.WeatherValuesState
import com.weatherapp.presentation.adapters.ForecastRcViewAdapter
import com.weatherapp.presentation.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch


class DaysFragment : Fragment() {

    private lateinit var binding: FragmentDaysBinding
    private val viewModel: WeatherViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDaysBinding.inflate(inflater, container, false)

        initRcView()

        return binding.root
    }

    private fun initRcView() {

        binding.daysRcView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.weatherValue.collect { networkResult ->
                binding.apply {
                    if (networkResult is NetworkResult.Success) {
                        when(val forecast = networkResult.data.forecastOfDaysValue) {
                            is WeatherValuesState.Success -> {
                                daysRcView.adapter = ForecastRcViewAdapter(forecast.data)
                                daysRcView.visibility = View.VISIBLE
                                daysForecastErrorIndicatorWrapper.errorIndicator.visibility = View.GONE
                            }
                            is WeatherValuesState.Error -> {
                                daysRcView.visibility = View.INVISIBLE
                                daysForecastErrorIndicatorWrapper.errorIndicator.setBackgroundResource(0)
                                daysForecastErrorIndicatorWrapper.errorIndicator.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }
    }
}