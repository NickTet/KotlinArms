package com.ppjun.android.arms.mvp

import android.arch.lifecycle.LifecycleObserver
import com.ppjun.android.arms.integration.ResponsitoryManager

/**
 * Created by ppjun on 3/12/18.
 */
class BaseModel : IModel, LifecycleObserver {

    protected var mRespositoryManager: ResponsitoryManager? = null
    override fun onDestroy() {

    }
}