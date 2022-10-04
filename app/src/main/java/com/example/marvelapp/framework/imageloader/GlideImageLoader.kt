package com.example.marvelapp.framework.imageloader

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import javax.inject.Inject

class GlideImageLoader @Inject constructor(): ImageLoader {

    override fun load(
        imageView: ImageView,
        imageUrl: String,
        placeHolder: Int,
        fallBack: Int
    ) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .placeholder(placeHolder)
            .fallback(fallBack)
            .into(imageView)
    }
}