package com.weatherapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import android.Manifest
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.weatherapp.R
import com.weatherapp.databinding.FragmentSelectCityBinding
import com.weatherapp.utils.LocationState
import com.weatherapp.presentation.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch


class SelectCityFragment : Fragment() {

    private lateinit var binding: FragmentSelectCityBinding
    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectCityBinding.inflate(inflater, container, false)

        initSelectCityFragment()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setFormVisibility()
    }

    private fun initSelectCityFragment() {
        viewModel.resetLocation()
        initGetLocationBtn()
        initSetLocationBtn()
        setLocationStateObserve()
    }

    private fun setFormVisibility() {
        binding.loading.loadingContainer.visibility = View.GONE
        binding.formContainer.visibility = View.VISIBLE
    }

    private fun setLocationStateObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.location.collect {
                when (it) {
                    is LocationState.Success -> {
                        findNavController().navigate(R.id.action_selectCityFragment_to_homeFragment)

                    }

                    is LocationState.NotEnoughRights -> {
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            101
                        )
                    }

                    is LocationState.Error -> {
                        Toast.makeText(
                            requireContext(),
                            "Something went wrong",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is LocationState.Idle -> Unit
                }
            }
        }
    }

    private fun initSetLocationBtn() {
        binding.continueBtn.setOnClickListener {
            if (binding.editText.text.isNotEmpty()) {
                viewModel.setLocation(binding.editText.text.toString())
            }
        }
    }

    private fun initGetLocationBtn() {
        binding.getLocBtn.setOnClickListener {
            binding.formContainer.visibility = View.INVISIBLE
            binding.loading.loadingContainer.visibility = View.VISIBLE
            viewModel.getLocation()
        }
    }


}