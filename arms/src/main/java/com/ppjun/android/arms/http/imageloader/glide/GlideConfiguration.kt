package com.ppjun.android.arms.http.imageloader.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.ppjun.android.arms.di.component.AppComponent
import com.ppjun.android.arms.utils.ArmsUtils
import java.io.InputStream

/**
 * Created by ppjun on 3/14/18.
 */


@GlideModule(glideName = "GlideArms")
class GlideConfiguration : AppGlideModule() {
   companion object {

       val IMAGE_DISK_CACHE_MAX_SIZE=10*1024*1024//图片缓存文件最大值为100Mb
   }

    override fun applyOptions(context: Context?, builder: GlideBuilder?) {
        super.applyOptions(context, builder)
    }


    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        var appComponent=ArmsUtils.obtainAppComponentFromContext(context)
registry.replace(GlideUrl::class.java,InputStream::class.java,
        OKHttpUrl)
    }


    override fun isManifestParsingEnabled()=false
}