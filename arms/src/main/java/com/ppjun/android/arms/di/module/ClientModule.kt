package com.ppjun.android.arms.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import dagger.Provides
import io.rx_cache2.internal.RxCache
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by ppjun on 3/7/18.
 */
abstract class ClientModule {
    companion object {
        val TIME_OUT = 10
    }


    @Singleton
    @Provides
    fun provideRetrofit(application: Application, retrofirConfiguration: RetrofitConfiguration, builder: Retrofit.Builder, client: OkHttpClient,
                        httpUrl: HttpUrl, gson: Gson): Retrofit {

        builder.baseUrl(httpUrl)
                .client(client)
        retrofirConfiguration?.configRetrofit(application,
                builder)
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))



        return builder.build()
    }


    interface RetrofitConfiguration {
        fun configRetrofit(context: Context, builder: Retrofit.Builder)
    }

    interface OkhttpConfiguration {
        fun configOkhttp(context: Context, builder: OkHttpClient.Builder)
    }

    interface RxCacheConfiguration {
        fun configRxCache(context: Context, rxcache: RxCache): RxCache
    }
}