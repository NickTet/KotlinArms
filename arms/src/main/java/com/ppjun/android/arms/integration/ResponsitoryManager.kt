package com.ppjun.android.arms.integration

import android.app.Application
import android.content.Context
import com.ppjun.android.arms.integration.cache.Cache
import com.ppjun.android.arms.integration.cache.CacheType
import com.ppjun.android.arms.utils.Preconditions
import io.rx_cache2.internal.RxCache
import retrofit2.Retrofit
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ppjun on 3/12/18.
 */


@Singleton
class ResponsitoryManager : IRespositoryManager {

    @Inject
    lateinit var mRetrofit: dagger.Lazy<Retrofit>
    @Inject
    lateinit var mRxCache: dagger.Lazy<RxCache>
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mCacheFactory: Cache.Factory
    lateinit var mRetrofitServiceCache: Cache<String, Any>
    lateinit var mCacheServiceCache: Cache<String, Any>

    @Inject
    constructor()

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param service
     * @param <T>
     * @return
     */
    override fun <T> obtainRetrofitService(service: Class<T>): T {
        if (mRetrofitServiceCache == null) {
            mRetrofitServiceCache = mCacheFactory.build(CacheType.RETROFIT_SERVICE_CACHE)
        }
        Preconditions.checkNotNull(mRetrofitServiceCache, "Cannot return null from a Cache.Factory#build(int) method")
        var retrofitService: T = mRetrofitServiceCache.get(service.canonicalName) as T
        if (retrofitService == null) {
            retrofitService = mRetrofit.get().create(service)
            mRetrofitServiceCache.put(service.canonicalName, retrofitService!!)
        }
        return retrofitService
    }


    override fun <T> obtainCacheService(cache: Class<T>): T {
        if (mCacheServiceCache == null) {
            mCacheServiceCache = mCacheFactory.build(CacheType.CACHE_SERVIVE_CACHE)
        }
        Preconditions.checkNotNull(mCacheServiceCache, "Cannot return null from a Cache.Factory#build(int) method")
        var cacheService:T = mCacheServiceCache.get(cache.canonicalName) as T
        if (cacheService == null) {
            cacheService == mRxCache.get().using(cache)
            mCacheServiceCache.put(cache.canonicalName, cacheService!!)

        }
        return cacheService

    }
    /**
     * 清理所有缓存
     */
    override fun clearAllCache() {
        mRxCache.get().evictAll()
    }

    override fun getContext(): Context {
        return mApplication
    }
}