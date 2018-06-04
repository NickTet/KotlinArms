package com.ppjun.android.arms.http.imageloader.glide

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.ppjun.android.arms.http.imageloader.BaseImageLoaderStrategy

/**
 * Created by ppjun on 3/14/18.
 */
class GlideImageLoaderStategy :BaseImageLoaderStrategy,GlideConfiguration{
    override fun <T> loadImage(context: Context, config: T) {
    }

    override fun <T> clear(context: Context, config: T) {
    }

    override fun applyGlideOptions(context: Context, builder: GlideBuilder) {
    }


}