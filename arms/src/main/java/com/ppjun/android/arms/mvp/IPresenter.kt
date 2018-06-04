package com.ppjun.android.arms.mvp

/**
 * Created by ppjun on 3/12/18.
 */
interface IPresenter {

    fun start()

    /**
     * 在框架中，activity 的ondestroy会默认调用iPresenter中的ondestroy
     */
    fun onDestroy()
}