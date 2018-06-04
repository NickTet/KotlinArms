package com.ppjun.android.arms.base.delegate

import android.os.Bundle
import android.support.annotation.Nullable

/**
 * Created by ppjun on 3/6/18.
 */
interface ActivityDelegate {

    companion object {
        val LAYOUT_LINEARLAYOUT = "LinearLayout"
        val LAYOUT_FRAMELAYOUT = "FrameLayout"
        val LAYOUT_RELATIVE = "RelativeLayout"
        val ACTIVITY_DELEGATE = "activity_delegate"
    }
    
    fun onCreate(@Nullable saveInstanceState: Bundle)
    fun onStart()
    fun onResume()
    fun onPause()
    fun onStop()
    fun onSaveInstanceState(outState: Bundle)
    fun onDestory()
}