package com.ppjun.android.arms.integration

import android.content.Context

/**
 * Created by ppjun on 3/7/18.
 */
interface IRespositoryManager {

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param service
     * @param <T>
     * @return
     */
    fun <T> obtainRetrofitService(service: Class<T>): T

    /**
     * 根据传入的 Class 获取对应的 RxCache service
     *
     * @param cache
     * @param <T>
     * @return
     */
    fun <T> obtainCacheService(cache: Class<T>): T

    /**
     * 清理所有缓存
     */
    fun clearAllCache()

    fun getContext(): Context
}