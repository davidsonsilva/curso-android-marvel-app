package com.example.marvelapp.framework.imageloader

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.example.marvelapp.R

interface ImageLoader {
    fun load(
        imageView: ImageView,
        imageUrl: String,
        @DrawableRes placeHolder: Int = R.drawable.ic_img_placeholder,
        @DrawableRes fallBack: Int = R.drawable.ic_img_loading_error
    )
}