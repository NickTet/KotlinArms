package com.ppjun.android.arms.integration.lifecycle

import android.support.annotation.NonNull
import io.reactivex.subjects.Subject

/**
 * Created by ppjun on 3/12/18.
 */
interface Lifecycleable<E> {
    @NonNull
    fun provideLifecycleSubject(): Subject<E>
}