package com.ppjun.android.arms.utils

import com.ppjun.android.arms.integration.lifecycle.ActivityLifecycleable
import com.ppjun.android.arms.integration.lifecycle.FragmentLifecycleable
import com.ppjun.android.arms.integration.lifecycle.Lifecycleable
import com.ppjun.android.arms.mvp.IView
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid
import io.reactivex.annotations.NonNull

/**
 * Created by ppjun on 3/12/18.
 */
class RxLifecycleUtil {

    private constructor() {
        throw IllegalStateException("you can't instantiate me!")
    }

    /**
     * 绑定 Activity 的指定生命周期
     *
     * @param view
     * @param event
     * @param <T>
     * @return
    </T> */
    fun <T> bindUntilEvent(@NonNull view: IView,
                           event: ActivityEvent): LifecycleTransformer<T> {
        Preconditions.checkNotNull(view, "view == null")
        return if (view is ActivityLifecycleable) {
            bindUntilEvent(view as ActivityLifecycleable, event)
        } else {
            throw IllegalArgumentException("view isn't ActivityLifecycleable")
        }
    }

    /**
     * 绑定 Fragment 的指定生命周期
     *
     * @param view
     * @param event
     * @param <T>
     * @return
    </T> */
    fun <T> bindUntilEvent(@NonNull view: IView,
                           event: FragmentEvent): LifecycleTransformer<T> {
        Preconditions.checkNotNull(view, "view == null")
        return if (view is FragmentLifecycleable) {
            bindUntilEvent(view as FragmentLifecycleable, event)
        } else {
            throw IllegalArgumentException("view isn't FragmentLifecycleable")
        }
    }

    fun <T, R> bindUntilEvent(@NonNull lifecycleable: Lifecycleable<R>,
                              event: R): LifecycleTransformer<T> {
        Preconditions.checkNotNull(lifecycleable, "lifecycleable == null")
        return RxLifecycle.bindUntilEvent(lifecycleable.provideLifecycleSubject(), event)
    }


    /**
     * 绑定 Activity/Fragment 的生命周期
     *
     * @param view
     * @param <T>
     * @return
    </T> */
    fun <T> bindToLifecycle(@NonNull view: IView): LifecycleTransformer<T> {
        Preconditions.checkNotNull(view, "view == null")
        return if (view is Lifecycleable<*>) {
            bindToLifecycle<T>(view as Lifecycleable<*>)
        } else {
            throw IllegalArgumentException("view isn't Lifecycleable")
        }
    }

    fun <T> bindToLifecycle(@NonNull lifecycleable: Lifecycleable<*>): LifecycleTransformer<T> {
        Preconditions.checkNotNull(lifecycleable, "lifecycleable == null")
        return if (lifecycleable is ActivityLifecycleable) {
            RxLifecycleAndroid.bindActivity((lifecycleable as ActivityLifecycleable).provideLifecycleSubject())
        } else if (lifecycleable is FragmentLifecycleable) {
            RxLifecycleAndroid.bindFragment((lifecycleable as FragmentLifecycleable).provideLifecycleSubject())
        } else {
            throw IllegalArgumentException("Lifecycleable not match")
        }
    }
}