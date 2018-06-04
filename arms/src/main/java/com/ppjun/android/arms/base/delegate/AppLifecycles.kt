package com.ppjun.android.arms.base.delegate

import android.app.Application
import android.content.Context

/**
 * Created by ppjun on 3/7/18.
 */
interface AppLifecycles {
    fun attachBaseContext(base: Context)

    fun onCreate(application: Application)

    fun onTerminate(application: Application)
}