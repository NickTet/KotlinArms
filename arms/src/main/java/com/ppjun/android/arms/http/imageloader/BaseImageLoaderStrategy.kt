package com.ppjun.android.arms.http.imageloader

import android.content.Context

/**
 * Created by ppjun on 3/7/18.
 */
interface BaseImageLoaderStrategy {

    fun <T> loadImage(context: Context, config: T)

    fun <T> clear(context: Context, config: T)
}