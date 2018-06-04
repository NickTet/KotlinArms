package com.ppjun.android.arms.base

import com.ppjun.android.arms.di.component.AppComponent

/**
 * Created by ppjun on 3/7/18.
 */
interface App {
    fun getAppComponent(): AppComponent
}