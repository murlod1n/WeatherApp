package com.weatherapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weatherapp.domain.common.WeatherValuesState
import com.weatherapp.R
import com.weatherapp.databinding.ForecastItemBinding
import com.weatherapp.presentation.common.loadImageFromInternet
import com.weatherapp.presentation.models.ForecastItemValue


class ForecastRcViewAdapter(private val data: List<WeatherValuesState<ForecastItemValue>>) :
    RecyclerView.Adapter<ForecastRcViewAdapter.Holder>() {

    class Holder(val view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ForecastItemBinding.bind(view)

        fun bind(item: WeatherValuesState<ForecastItemValue>) {

            binding.apply {
                when (item) {
                    is WeatherValuesState.Success -> {
                        itemIcon.loadImageFromInternet("https:${item.data.conditionValue.icon}")
                        itemDate.text = item.data.date
                        itemTemp.text = if (item.data.tempC.isEmpty()) "" else view.context.resources.getString(R.string.circle, item.data.tempC)
                    }
                    is WeatherValuesState.Error -> {
                        itemDate.text = "Oops, Error"
                        itemIcon.setImageResource(R.drawable.icons8____100)

                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.forecast_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

}

