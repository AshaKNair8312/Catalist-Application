package com.example.catalistapplication.utils

import android.annotation.SuppressLint
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.catalistapplication.R
import com.google.android.material.textview.MaterialTextView
import java.text.SimpleDateFormat
import java.util.*


@BindingAdapter("imageFromUrl")
fun setImageFromUrl(imgView: AppCompatImageView, imgUrl: String) {
    Glide.with(imgView.context)
        .load(imgUrl)
        .placeholder(R.drawable.app_logo)
        .error(R.drawable.app_logo)
        .circleCrop()
        .apply(
            RequestOptions()

        )
        .into(imgView)
}

@BindingAdapter("squareImageFromUrl")
fun setSquareImageFromUrl(imgView: AppCompatImageView, imgUrl: String) {
    Glide.with(imgView.context)
        .load(imgUrl)
        .placeholder(R.drawable.app_logo)
        .error(R.drawable.app_logo)
        .apply(
            RequestOptions()

        )
        .into(imgView)
    imgView.clipToOutline = true
}

@SuppressLint("SimpleDateFormat", "SetTextI18n")
@BindingAdapter("stringtoDate")
fun stringToDate(textview: MaterialTextView, text: String) {
    try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val date = inputFormat.parse(text)
        val newDate = date?.let { outputFormat.format(it) }
        textview.text = "Joined On : $newDate"
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@BindingAdapter("capitalizeText")
fun capitalizeText(textview: MaterialTextView, text: String) {
    textview.text = text.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
}


