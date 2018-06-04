package com.ppjun.android.arms.integration.cache

/**
 * Created by ppjun on 3/6/18.
 */
interface Cache<K, V> {
    interface Factory{
        fun build(cacheType: CacheType):Cache<String, Any>
    }
    fun size(): Int
    fun getMaxSize(): Int

    fun get(key: K): V
    fun remove(key: K): V?

    fun containsKey(key: K): Boolean

    fun keySet(): Set<K>

    fun clear()
    fun put(key: K, value: V): V?


}