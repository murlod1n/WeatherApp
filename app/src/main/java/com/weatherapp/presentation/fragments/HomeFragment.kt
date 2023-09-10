package com.weatherapp.presentation.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.weatherapp.R
import com.weatherapp.databinding.FragmentHomeBinding
import com.weatherapp.domain.common.NetworkResult
import com.weatherapp.presentation.adapters.ForecastViewPageAdapter
import com.weatherapp.presentation.common.initCustomProgressBar
import com.weatherapp.presentation.common.loadImageFromInternet
import com.weatherapp.presentation.models.CurrentValue
import com.weatherapp.presentation.viewmodel.WeatherViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.weatherapp.domain.common.WeatherValuesState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: WeatherViewModel by activityViewModels()

    private val tabsList = listOf("Hours", "Days")
    private val listOfFragments = listOf(HoursFragment(), DaysFragment())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        initFragment()

        return binding.root
    }

    private fun initFragment() {
        viewModel.getWeatherValue()
        setHomeFragmentContainer()
        setUpdateBtnListener()
        setBackArrowBtnListener()
        setTabLayout()
    }

    private fun setHomeFragmentContainer() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.weatherValue.collect {
                when (it) {
                    is NetworkResult.Success -> {
                        binding.currentWeatherContainer.visibility = View.VISIBLE
                        setLocationTitle(it.data.locationValue)
                        setCurrentWeather(currentValue = it.data.currentValue)
                        stopLoadingAnimation()
                    }
                    is NetworkResult.Error -> {
                        stopLoadingAnimation()
                        binding.homeFragmentErrorIndicatorWrapper.homeErrorIndicator.visibility =
                            View.VISIBLE
                        binding.homeFragmentErrorIndicatorWrapper.errBackBtn.setOnClickListener {
                            findNavController().popBackStack()
                        }
                        binding.currentWeatherContainer.visibility = View.GONE
                    }
                    is NetworkResult.Loading -> {
                        startLoadingAnimation()
                    }
                }
            }
        }
    }

    private fun setLocationTitle(location: String) {
        val date = "${LocalDate.now().dayOfMonth} ${LocalDate.now().month}"
        binding.cityName.text = location
        binding.dateTitle.text = date
    }

    private fun setBackArrowBtnListener() {
        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpdateBtnListener() {
        binding.updateIcon.setOnClickListener {
            viewModel.getWeatherValue()
        }
    }

    private fun setTabLayout() {
        binding.apply {
            forecastVp.adapter = ForecastViewPageAdapter(this@HomeFragment, listOfFragments)
            TabLayoutMediator(tabLayout, forecastVp) { tab, position ->
                tab.text = tabsList[position]
            }.attach()
        }
    }

    private fun setCurrentWeatherCards(data: CurrentValue) {
        binding.apply {

            windSpeed.text = getString(R.string.km_format, data.windKph.toString())
            humidityValue.text = getString(R.string.proc_format, data.humidity.toString())
            cloudValue.text = getString(R.string.proc_format, data.cloud.toString())

            windProgressbar.initCustomProgressBar(data.windKph.toInt(), 30, 20)
            humidityProgressbar.initCustomProgressBar(data.humidity)
            cloudProgressbar.initCustomProgressBar(data.cloud)

        }
    }

    private fun setCurrentWeather(currentValue: WeatherValuesState<CurrentValue>) {
        binding.apply {
            when (currentValue) {
                is WeatherValuesState.Success -> {
                    setCurrentWeatherCards(currentValue.data)
                    tempText.text = getString(R.string.circle, currentValue.data.tempC.toString())
                    weatherDesc.text = currentValue.data.condition.text
                    currentWeatherIcon.loadImageFromInternet("https:${currentValue.data.condition.icon}")
                    curWeatherErrorIndicatorWrapper.errorIndicator.visibility = View.GONE
                    tempContainer.visibility = View.VISIBLE
                    cardsContainer.visibility = View.VISIBLE
                }

                is WeatherValuesState.Error -> {
                    curWeatherErrorIndicatorWrapper.errorIndicator.visibility = View.VISIBLE
                    tempContainer.visibility = View.INVISIBLE
                    cardsContainer.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun startLoadingAnimation() {
        val animation = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.transparent_anim
        )
        binding.apply {

            windProgressbar.visibility = View.INVISIBLE
            windSpeed.visibility = View.INVISIBLE
            windTitle.visibility = View.INVISIBLE

            humidityProgressbar.visibility = View.INVISIBLE
            humidityTitle.visibility = View.INVISIBLE
            humidityValue.visibility = View.INVISIBLE

            cloudProgressbar.visibility = View.INVISIBLE
            cloudTitle.visibility = View.INVISIBLE
            cloudValue.visibility = View.INVISIBLE


            updateIcon.visibility = View.INVISIBLE
            tempText.visibility = View.INVISIBLE
            weatherDesc.visibility = View.INVISIBLE
            currentWeatherIcon.visibility = View.INVISIBLE

            cityNameContainer.setBackgroundResource(R.drawable.rounded_background)
            cityName.visibility = View.INVISIBLE
            dateTitle.visibility = View.INVISIBLE

            currentWeatherContainer.startAnimation(animation)
        }
    }

    private fun stopLoadingAnimation() {
        binding.apply {

            currentWeatherContainer.animation.cancel()

            windProgressbar.visibility = View.VISIBLE
            windSpeed.visibility = View.VISIBLE
            windTitle.visibility = View.VISIBLE


            humidityProgressbar.visibility = View.VISIBLE
            humidityTitle.visibility = View.VISIBLE
            humidityValue.visibility = View.VISIBLE


            cloudProgressbar.visibility = View.VISIBLE
            cloudTitle.visibility = View.VISIBLE
            cloudValue.visibility = View.VISIBLE


            updateIcon.visibility = View.VISIBLE
            tempText.visibility = View.VISIBLE
            weatherDesc.visibility = View.VISIBLE
            currentWeatherIcon.visibility = View.VISIBLE

            cityNameContainer.setBackgroundResource(0)
            cityName.visibility = View.VISIBLE
            dateTitle.visibility = View.VISIBLE
        }
    }

}