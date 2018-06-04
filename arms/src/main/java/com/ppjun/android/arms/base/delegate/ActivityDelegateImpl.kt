package com.ppjun.android.arms.base.delegate

import android.app.Activity
import android.os.Bundle
import com.ppjun.android.arms.utils.ArmsUtils
import org.simple.eventbus.EventBus

/**
 * Created by ppjun on 3/6/18.
 */
class ActivityDelegateImpl() : ActivityDelegate {
    var mActivity: Activity? = null
    var iActivity: IActivity? = null


    constructor(activity: Activity) : this() {

        this.mActivity = activity
        this.iActivity = activity as IActivity

    }

    override fun onCreate(saveInstanceState: Bundle) {
        if (iActivity!!.useEventBus()) {
            EventBus.getDefault().register(mActivity)
        }
        iActivity!!.setupActivityComponent(ArmsUtils.obtainAppComponentFromContext(mActivity!!))
    }

    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    override fun onSaveInstanceState(outState: Bundle) {
    }

    override fun onDestory() {

        if (iActivity != null) {
            EventBus.getDefault().unregister(mActivity)
            mActivity = null
            iActivity = null
        }
    }


}