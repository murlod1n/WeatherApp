package com.weatherapp.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ForecastViewPageAdapter(fragment: Fragment, private val listOfFragments: List<Fragment>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount() = listOfFragments.size
    override fun createFragment(position: Int) = listOfFragments[position]
}