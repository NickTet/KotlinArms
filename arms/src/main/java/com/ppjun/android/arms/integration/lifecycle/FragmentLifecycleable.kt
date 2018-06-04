package com.ppjun.android.arms.integration.lifecycle

import android.support.v4.app.Fragment
import com.trello.rxlifecycle2.android.FragmentEvent

/**
 * Created by ppjun on 3/12/18.
 */
interface FragmentLifecycleable :Lifecycleable<FragmentEvent> {
}