package com.weatherapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.weatherapp.databinding.FragmentHoursBinding
import com.weatherapp.domain.common.NetworkResult
import com.weatherapp.domain.common.WeatherValuesState
import com.weatherapp.presentation.adapters.ForecastRcViewAdapter
import com.weatherapp.presentation.models.ConditionValue
import com.weatherapp.presentation.models.ForecastItemValue
import com.weatherapp.presentation.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch


class HoursFragment : Fragment() {

    private lateinit var binding: FragmentHoursBinding
    private val viewModel: WeatherViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHoursBinding.inflate(inflater, container, false)

        initRcView()

        return binding.root
    }


    private fun initRcView() {
        val fakeItem = listOf(
            WeatherValuesState.Success(
            ForecastItemValue("", "", ConditionValue("", "", 1))
        ))

        binding.hoursRcView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.weatherValue.collect { networkResult ->
                binding.apply {
                    hoursRcView.adapter = ForecastRcViewAdapter(fakeItem)
                    if (networkResult is NetworkResult.Success) {
                        when(val forecast = networkResult.data.forecastOfHoursValue) {
                            is WeatherValuesState.Success -> {
                                hoursRcView.adapter = ForecastRcViewAdapter(forecast.data)
                                hoursRcView.visibility = View.VISIBLE
                                hoursForecastErrorIndicatorWrapper.errorIndicator.visibility = View.GONE
                            }
                            is WeatherValuesState.Error -> {
                                hoursRcView.visibility = View.INVISIBLE
                                hoursForecastErrorIndicatorWrapper.errorIndicator.setBackgroundResource(0)
                                hoursForecastErrorIndicatorWrapper.errorIndicator.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }
    }
}

