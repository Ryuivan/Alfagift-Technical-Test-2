package com.example.technicaltest2.util.glide

import android.widget.ImageView

/**
 * ImageLoader interface defines a contract for loading images into an ImageView.
 */
interface ImageLoader {
    fun loadImage(imageUrl: String, imageView: ImageView)
}
