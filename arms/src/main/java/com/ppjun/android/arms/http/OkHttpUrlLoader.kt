package com.ppjun.android.arms.http

import android.content.Context
import android.graphics.Color
import android.widget.TextView
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import okhttp3.Call
import okhttp3.OkHttpClient
import java.io.InputStream

/**
 * Created by ppjun on 3/14/18.
 */
class OkHttpUrlLoader : ModelLoader<GlideUrl, InputStream> {
    lateinit var client: Call.Factory

    constructor(client: Call.Factory) {
        this.client = client
    }

    override fun buildLoadData(model: GlideUrl, width: Int,
                               height: Int,
                               options: Options) =
            ModelLoader.LoadData(model, OkHttpStreamFetcher(client, model));

    override fun handles(model: GlideUrl) = true


    class Factory : ModelLoaderFactory<GlideUrl, InputStream> {
        @Volatile
        var internalClient: Call.Factory? = null
        var client: Call.Factory? = null

        constructor() {
            (getInsternalClient())
        }

        constructor(client: Call.Factory) {
            this.client = client
        }

        fun getInsternalClient(): Call.Factory? {
            if (internalClient == null) {
                synchronized(Factory::class) {
                    if (internalClient == null) {
                        internalClient = OkHttpClient()
                    }
                }
            }
            return internalClient
        }

        override fun build(multiFactory: MultiModelLoaderFactory) = OkHttpUrlLoader(client!!)

        override fun teardown() {



        }

    }


}