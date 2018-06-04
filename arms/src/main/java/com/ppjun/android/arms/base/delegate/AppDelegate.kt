package com.ppjun.android.arms.base.delegate

import android.app.Application
import android.content.Context
import com.ppjun.android.arms.base.App
import com.ppjun.android.arms.di.component.AppComponent
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by ppjun on 3/7/18.
 */
class AppDelegate : App, AppLifecycles {

    var mApplicaiton: Application? = null
    var mAppComponent: AppComponent? = null

    @Inject
    @field:Named("ActivityLifecycle")
    var mActivityLifecycle :Application.ActivityLifecycleCallbacks ?=null

    @Inject
    @field:Named("ActivityLifecycleForRxLifecycle")
    var mActivityLifecycleForRxLifecycle :Application.ActivityLifecycleCallbacks ?=null


    override fun getAppComponent(): AppComponent {
        return mAppComponent!!
    }


    override fun attachBaseContext(base: Context) {
    }

    override fun onCreate(application: Application) {
    }

    override fun onTerminate(application: Application) {
    }
}