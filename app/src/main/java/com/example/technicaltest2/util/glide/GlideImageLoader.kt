package com.example.technicaltest2.util.glide

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * GlideImageLoader is an implementation of the ImageLoader interface.
 * It uses the Glide library to load images into an ImageView.
 */
class GlideImageLoader(private val context: Context) : ImageLoader {

    /**
     * Loads an image from a URL into the provided ImageView using Glide.
     */
    override fun loadImage(imageUrl: String, imageView: ImageView) {
        Glide.with(context)
            .load(imageUrl)
            .centerCrop()
            .into(imageView)
    }
}
