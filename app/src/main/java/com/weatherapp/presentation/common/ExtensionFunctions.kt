package com.weatherapp.presentation.common

import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import com.squareup.picasso.Picasso

fun ImageView.loadImageFromInternet(url: String) {
    Picasso
        .get()
        .load(url)
        .fit()
        .centerCrop()
        .into(this)
}

fun ProgressBar.initCustomProgressBar(
    currentValue: Int,
    maxValue: Int = 100,
    redIndicatorValue: Int = 70
) {
    this.apply {
        max = maxValue
        progress = currentValue
        progressTintList = if (currentValue < redIndicatorValue) {
            ColorStateList.valueOf(Color.parseColor("#45d1c1"))
        } else {
            ColorStateList.valueOf(Color.parseColor("#e84f53"))
        }
    }
}