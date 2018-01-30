package com.klibre.adapters

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.klibre.R
import com.squareup.picasso.Picasso

/**
 * Created by Miguel on 29/1/2018.
 */

class ImageBindingAdapter {
    companion object {
        @BindingAdapter("bind:coverUrl")
        fun loadImage(cover: ImageView, coverURL: String) {
            if (!coverURL.isEmpty()){
                Picasso.with(cover.getContext())
                        .load(coverURL)
                        .error(R.mipmap.ic_not_image)
                        .placeholder(R.mipmap.ic_not_image)
                        .into(cover)
            }
        }
    }
}