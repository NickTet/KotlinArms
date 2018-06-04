package com.ppjun.android.arms.http.imageloader.glide

import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.ppjun.android.arms.http.imageloader.ImageConfig



/**将150行代码缩减到50行
 * Created by ppjun on 3/14/18.
 */
class ImageConfigImpl {

    private constructor(url: String, imageView: ImageView, placeHolder: Int, errorPic: Int, cacheStrategy: Int,
                        fallback: Int, transformation: BitmapTransformation,
                        imageViews: Array<ImageView>,
                        isClearMemory: Boolean,
                        isclearDiskCache: Boolean)

    constructor(builder: Builder) : this(builder.url, builder.imageView, builder.placeholder, builder.errorPic
            , builder.cacheStrategy, builder.fallback, builder.transformation, builder.imageViews, builder.isClearMemory, builder.isClearDiskCache)


    companion object {
        fun build(init: Builder.() -> Unit) = Builder(init).build()
    }

    class Builder private constructor() {

        constructor(init: Builder.() -> Unit) : this() {
            init()
        }

        lateinit var url: String
        lateinit var imageView: ImageView
        var placeholder: Int = 0
        var errorPic: Int = 0
        var cacheStrategy: Int = 0
        var fallback: Int = 0
        lateinit var transformation: BitmapTransformation
        lateinit var imageViews: Array<ImageView>
        var isClearMemory: Boolean = false
        var isClearDiskCache: Boolean = false

        fun url(init: Builder.() -> String) = apply { url = init() }
        fun imageView(init: Builder.() -> ImageView) = apply { imageView = init() }
        fun placeholder(init: Builder.() -> Int) = apply { placeholder = init() }
        fun errorPic(init: Builder.() -> Int) = apply { errorPic = init() }
        fun cacheStrategy(init: Builder.() -> Int) = apply { cacheStrategy = init() }
        fun fallback(init: Builder.() -> Int) = apply { fallback = init() }
        fun transformation(init: Builder.() -> BitmapTransformation) = apply { transformation = init() }
        fun imageViews(init: Builder.() -> Array<ImageView>) = apply { imageViews = init() }
        fun clearMemory(init: Builder.() -> Boolean) = apply { isClearMemory = init() }
        fun clearDiskCache(init: Builder.() -> Boolean) = apply { isClearDiskCache = init() }
        fun build() = ImageConfigImpl(this)
    }
}