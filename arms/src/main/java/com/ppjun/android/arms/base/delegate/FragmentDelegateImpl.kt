package com.ppjun.android.arms.base.delegate

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import butterknife.ButterKnife
import butterknife.Unbinder
import com.ppjun.android.arms.utils.ArmsUtils
import org.simple.eventbus.EventBus
import timber.log.Timber

/**
 * Created by ppjun on 3/7/18.
 */
class FragmentDelegateImpl : FragmentDelegate {


    var mFragmentManager: FragmentManager? = null
    var mFragment: Fragment? = null
    var iFragment: IFragment? = null
    var mUnbinder: Unbinder? = null

    constructor(fragment: Fragment, fragmentManager: FragmentManager) {
        this.mFragment = fragment
        this.mFragmentManager = fragmentManager
        this.iFragment = fragment as IFragment

    }


    override fun onAttach(context: Context) {
    }

    override fun onCreate(savedInstanceState: Bundle) {
        if (iFragment!!.useEvebtBus()) {
            EventBus.getDefault().register(mFragment)
        }
        iFragment?.setupFragmentComponent(ArmsUtils.obtainAppComponentFromContext(mFragment?.activity!!))
    }

    override fun onCreateView(view: View, savedInstanceState: Bundle) {
        if (view != null) {
            mUnbinder = ButterKnife.bind(mFragment!!, view)
        }
    }

    override fun onActivityCreate(savedInstanceState: Bundle) {
        iFragment?.initData(savedInstanceState)
    }

    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    override fun onSaveInstanceState() {
    }

    override fun onDestroyView() {
        try {
            if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
                mUnbinder?.let { it.unbind() }
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            Timber.w("onDestroy" + e.message)
        }
    }

    override fun onDestroy() {
        if (iFragment != null && iFragment!!.useEvebtBus()) {
            EventBus.getDefault().unregister(mFragment)
            mFragmentManager = null
            mFragment = null
            iFragment = null
            mUnbinder = null

        }
    }

    override fun onDetach() {
    }

    override fun isAdded(): Boolean {
        return true
    }
}