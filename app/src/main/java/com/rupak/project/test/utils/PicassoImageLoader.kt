package com.rupak.project.test.utils

import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

/**
 * Created by Rupak Adhikari.
 */
class PicassoImageLoader(private val picasso: Picasso) : ImageLoader {





    private class FetchCallback(val delegate: (Boolean) -> Unit): Callback {
        override fun onSuccess() {
            delegate(true)
        }

        override fun onError() {
            delegate(false)
        }

    }

    override fun load(url: String?, imageView: ImageView, callback: (Boolean) -> Unit) {
        picasso.load(url).into(imageView, FetchCallback(callback))
    }

    override fun load(url: String?, imageView: ImageView, fadeEffect: Boolean) {
        if (fadeEffect)
            picasso.load(url).into(imageView)
        else
            picasso.load(url).noFade().into(imageView)
    }
}