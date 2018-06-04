package com.ppjun.android.arms.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by ppjun on 3/7/18.
 */
@Module
abstract class AppModule {


    @Singleton
    @Provides
    fun provideGson(application: Application, gsonConfiguration: GsonConfiguration): Gson {
        var builder = GsonBuilder()
        gsonConfiguration?.configGson(application, builder)
        return builder.create()
    }


    interface GsonConfiguration {
        fun configGson(context: Context, gsonBuilder: GsonBuilder)
    }
}