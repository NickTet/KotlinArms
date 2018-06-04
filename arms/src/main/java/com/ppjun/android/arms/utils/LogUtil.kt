package com.ppjun.android.arms.utils

import android.text.TextUtils
import android.util.Log

/**
 * Created by ppjun on 3/12/18.
 */
class LogUtil {

    private constructor() {
        throw IllegalStateException("you can't instantiate me!")
    }

    private val isLog = true
    val DEFAULT_TAG = "MVPArms"

    fun debugInfo(tag: String, msg: String) {
        if (!isLog || TextUtils.isEmpty(msg)) return
        Log.d(tag, msg)

    }

    fun debugInfo(msg: String) {
        debugInfo(DEFAULT_TAG, msg)
    }

    fun warnInfo(tag: String, msg: String) {
        if (!isLog || TextUtils.isEmpty(msg)) return
        Log.w(tag, msg)

    }

    fun warnInfo(msg: String) {
        warnInfo(DEFAULT_TAG, msg)
    }

    /**
     * 所以这里使用自己分节的方式来输出足够长度的message
     *
     * @param tag
     * @param str void
     */
    fun debugLongInfo(tag: String, str: String) {
        var str = str
        if (!isLog) return
        str = str.trim { it <= ' ' }
        var index = 0
        val maxLength = 3500
        var sub: String
        while (index < str.length) {
            // java的字符不允许指定超过总的长度end
            if (str.length <= index + maxLength) {
                sub = str.substring(index)
            } else {
                sub = str.substring(index, index + maxLength)
            }

            index += maxLength
            Log.d(tag, sub.trim { it <= ' ' })
        }
    }

    fun debugLongInfo(str: String) {
        debugLongInfo(DEFAULT_TAG, str)
    }

}