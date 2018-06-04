package com.ppjun.android.arms.mvp

/**
 * Created by ppjun on 3/12/18.
 */
interface IModel {

    /**
     * 在框架中 {@link BasePresenter#onDestroy()} 时会默认调用 {@link IModel#onDestroy()}
     */
    fun onDestroy()
}