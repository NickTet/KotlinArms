package com.ppjun.android.arms.di.module

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import com.ppjun.android.arms.http.BaseUrl
import com.ppjun.android.arms.http.GlobalHttpHandler
import com.ppjun.android.arms.http.imageloader.BaseImageLoaderStrategy
import com.ppjun.android.arms.http.log.FormatPrinter
import com.ppjun.android.arms.http.log.RequestInterceptor
import com.ppjun.android.arms.integration.cache.Cache
import com.ppjun.android.arms.utils.Preconditions
import dagger.Module
import dagger.Provides
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener
import okhttp3.HttpUrl
import okhttp3.Interceptor
import java.io.File
import javax.inject.Singleton

/**
 * Created by ppjun on 3/7/18.
 */
@Module
class GlobalConfigModule {
    var mApiUrl: HttpUrl
    var mBaseUrl: BaseUrl
    var mLoaderStrategy: BaseImageLoaderStrategy
    var mHandler: GlobalHttpHandler
    var mInterceptor: List<Interceptor>
    var mErrorListener: ResponseErrorListener
    var mCacheFile: File
    var mRetrofitConfiguration: ClientModule.RetrofitConfiguration
    var mOkhttpConfiguration: ClientModule.OkhttpConfiguration
    var mRxCacheConfiguration: ClientModule.RxCacheConfiguration
    var mGsonConfiguration: AppModule.GsonConfiguration
    var mPrintHttpLogLevel: RequestInterceptor.Level
    var mFormatPrinter: FormatPrinter
    var mCacheForcatory: Cache.Factory

    constructor(builder: Builder) {
        mApiUrl = builder.apiUrl
        mBaseUrl = builder.baseUrl
        mLoaderStrategy = builder.imageLoaderStrategy
        mHandler = builder.globalHttpHandler
        mInterceptor = builder.interceptors
        mErrorListener = builder.responseErrorListener
        mCacheFile = builder.cacheFile
        mRetrofitConfiguration = builder.retrofitConfiguration
        mOkhttpConfiguration = builder.okhttpConfiguration
        mRxCacheConfiguration = builder.rxcacheConfiguration
        mGsonConfiguration = builder.gsonConfiguration
        mPrintHttpLogLevel = builder.printHttpLogLevel
        mFormatPrinter = builder.formatPrinter
        mCacheForcatory = builder.cacheFactory

    }


    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }

    @Singleton
    @Provides
    fun provideInterceptors(): List<Interceptor> {
        return mInterceptor
    }


    /**
     * 提供 BaseUrl,默认使用 <"https://api.github.com/">
     *
     * @return
     */
    @Singleton
    @Provides
    fun provideBaseUrl(): HttpUrl {
        if (mBaseUrl != null) {
            var httpUrl = mBaseUrl.url()
            if (httpUrl != null) {
                return httpUrl
            }
        }
        return if (mApiUrl == null) HttpUrl.parse("https://api.github.com/")!! else mApiUrl
    }

    /**
     * 提供图片加载框架,默认使用 {@link Glide}
     *
     * @return
     */
    @Singleton
    @Provides
    fun provideImageLoaderStrategy(): BaseImageLoaderStrategy {
        return if (mLoaderStrategy == null) mLoaderStrategy else mLoaderStrategy
    }

    @Singleton
    @Provides
    fun provideGlobalHttpHandler() :GlobalHttpHandler{
        return mHandler
    }

    @Singleton
    @Provides
    fun provideCacheFile():File{
        return mCacheFile
    }

    class Builder {
        lateinit var apiUrl: HttpUrl
        lateinit var baseUrl: BaseUrl
        lateinit var imageLoaderStrategy: BaseImageLoaderStrategy
        lateinit var globalHttpHandler: GlobalHttpHandler
        lateinit var interceptors: ArrayList<Interceptor>
        lateinit var responseErrorListener: ResponseErrorListener
        lateinit var cacheFile: File
        lateinit var retrofitConfiguration: ClientModule.RetrofitConfiguration
        lateinit var okhttpConfiguration: ClientModule.OkhttpConfiguration
        lateinit var rxcacheConfiguration: ClientModule.RxCacheConfiguration
        lateinit var gsonConfiguration: AppModule.GsonConfiguration
        lateinit var printHttpLogLevel: RequestInterceptor.Level
        lateinit var formatPrinter: FormatPrinter
        lateinit var cacheFactory: Cache.Factory


        fun baseUrl(baseUrl: String): Builder {
            if (TextUtils.isEmpty(baseUrl)) {
                throw NullPointerException("baseUrl can not be empty")
            }
            this.apiUrl = HttpUrl.parse(baseUrl)!!
            return this
        }

        fun baseUrl(bseUrl: BaseUrl): Builder {
            this.baseUrl = Preconditions.checkNotNull(baseUrl, BaseUrl::class.java.canonicalName, "can not be null")

            return this
        }

        fun imageLoaderStrategy(loadStrategy: BaseImageLoaderStrategy): Builder {
            this.imageLoaderStrategy = loadStrategy
            return this
        }

        fun globalHttpHandle(httpHandler: GlobalHttpHandler): Builder {
            this.globalHttpHandler = httpHandler
            return this
        }

        fun addInterceptor(interceptor: Interceptor): Builder {
            if (interceptors == null) {
                interceptors = arrayListOf<Interceptor>()
            }
            interceptors.add(interceptor)
            return this
        }

        fun responseErrorListener(listener: ResponseErrorListener): Builder {
            this.responseErrorListener = listener

            return this
        }

        fun cacheFile(cacheFile: File): Builder {
            this.cacheFile = cacheFile
            return this
        }

        fun retrofitConfiguration(retrofitConfiguration: ClientModule.RetrofitConfiguration): Builder {
            this.retrofitConfiguration = retrofitConfiguration
            return this
        }

        fun okhttpConfiguration(okhttpConfiguration: ClientModule.OkhttpConfiguration): Builder {
            this.okhttpConfiguration = okhttpConfiguration
            return this;
        }

        fun rxCacheConfiguration(rxCacheConfiguration: ClientModule.RxCacheConfiguration
        ): Builder {
            this.rxcacheConfiguration = rxcacheConfiguration
            return this
        }

        fun gsonConfiguration(gsonConfiguration: AppModule.GsonConfiguration): Builder {
            this.gsonConfiguration = gsonConfiguration
            return this
        }

        fun printHttpLogLevel(printHttpLogLevel: RequestInterceptor.Level): Builder {
            this.printHttpLogLevel = Preconditions.checkNotNull(printHttpLogLevel, "The printHttpLogLevel can not be null, use RequestInterceptor.Level.NONE instead.")
            return this
        }

        fun formatPrinter(formatPrinter: FormatPrinter): Builder {
            this.formatPrinter = Preconditions.checkNotNull(formatPrinter, FormatPrinter::class.java.canonicalName + "can't be null")
            return this
        }

        fun cacheFactory(cacheFactory: Cache.Factory): Builder {
            this.cacheFactory = cacheFactory
            return this
        }


        fun build(): GlobalConfigModule {
            return GlobalConfigModule(this)
        }
    }
}