package com.ppjun.android.arms.integration.cache

import android.app.ActivityManager
import android.content.Context

/**
 * Created by ppjun on 3/6/18.
 */
interface CacheType {
    //定义方法
    fun getCacheTypeId(): Int

    fun calculateCacheSize(context: Context): Int

    companion object {

        //定义变量
        val RETROFIT_SERVICE_CACHE_TYPE_ID = 0
        val CACHE_SERVIVE_CACHE_TYPE_ID = 1
        val EXTRAS_TYPE_ID = 2
        val ACTIVITY_CACHE_TYPE_ID = 3
        val FRAGMENT_CACHE_TYPE_ID = 4


        val RETROFIT_SERVICE_CACHE = object : CacheType {
            val MAX_SIZE = 150
            val MAX_SIZE_MULTIPLIER = 0.002f
            override fun getCacheTypeId(): Int {

                return RETROFIT_SERVICE_CACHE_TYPE_ID
            }

            override fun calculateCacheSize(context: Context): Int {
                var activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                var targetMemoryCacheSize = (activityManager.memoryClass * MAX_SIZE_MULTIPLIER * 1024).toInt()
                if (targetMemoryCacheSize >= MAX_SIZE) {
                    return MAX_SIZE
                }
                return targetMemoryCacheSize
            }

        }

        val  CACHE_SERVIVE_CACHE = object : CacheType {
            val MAX_SIZE = 150
            val MAX_SIZE_MULTIPLIER = 0.002f
            override fun getCacheTypeId(): Int {

                return  CACHE_SERVIVE_CACHE_TYPE_ID
            }

            override fun calculateCacheSize(context: Context): Int {
                var activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                var targetMemoryCacheSize = (activityManager.memoryClass * MAX_SIZE_MULTIPLIER * 1024).toInt()
                if (targetMemoryCacheSize >= MAX_SIZE) {
                    return MAX_SIZE
                }
                return targetMemoryCacheSize
            }

        }

        val  EXTRAS = object : CacheType {
            val MAX_SIZE = 500
            val MAX_SIZE_MULTIPLIER = 0.005f
            override fun getCacheTypeId(): Int {

                return  EXTRAS_TYPE_ID
            }

            override fun calculateCacheSize(context: Context): Int {
                var activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                var targetMemoryCacheSize = (activityManager.memoryClass * MAX_SIZE_MULTIPLIER * 1024).toInt()
                if (targetMemoryCacheSize >= MAX_SIZE) {
                    return MAX_SIZE
                }
                return targetMemoryCacheSize
            }

        }

        val  ACTIVITY_CACHE = object : CacheType {
            val MAX_SIZE = 80
            val MAX_SIZE_MULTIPLIER = 0.0008f
            override fun getCacheTypeId(): Int {

                return  ACTIVITY_CACHE_TYPE_ID
            }

            override fun calculateCacheSize(context: Context): Int {
                var activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                var targetMemoryCacheSize = (activityManager.memoryClass * MAX_SIZE_MULTIPLIER * 1024).toInt()
                if (targetMemoryCacheSize >= MAX_SIZE) {
                    return MAX_SIZE
                }
                return targetMemoryCacheSize
            }

        }
        val  FRAGMENT_CACHE = object : CacheType {
            val MAX_SIZE = 80
            val MAX_SIZE_MULTIPLIER = 0.0008f
            override fun getCacheTypeId(): Int {

                return  FRAGMENT_CACHE_TYPE_ID
            }

            override fun calculateCacheSize(context: Context): Int {
                var activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                var targetMemoryCacheSize = (activityManager.memoryClass * MAX_SIZE_MULTIPLIER * 1024).toInt()
                if (targetMemoryCacheSize >= MAX_SIZE) {
                    return MAX_SIZE
                }
                return targetMemoryCacheSize
            }

        }
    }


}