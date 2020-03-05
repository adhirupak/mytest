package com.rupak.project.test.utils
import android.widget.ImageView

/**
 * Created by Rupak Adhikari.
 */
interface ImageLoader {
    fun load(url: String?, imageView: ImageView, callback: (Boolean) -> Unit)
    fun load(url: String?, imageView: ImageView, fadeEffect: Boolean = true)
}

