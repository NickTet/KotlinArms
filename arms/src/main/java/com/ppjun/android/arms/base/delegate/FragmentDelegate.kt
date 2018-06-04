package com.ppjun.android.arms.base.delegate

import android.content.Context
import android.os.Bundle
import android.support.annotation.Nullable
import android.view.View

/**
 * Created by ppjun on 3/7/18.
 */
interface FragmentDelegate {

    companion object {
        val FRAGEMNT_DELEGATE = "fragment_delegate"
    }

    fun onAttach(context: Context)
    fun onCreate(@Nullable savedInstanceState: Bundle)
    fun onCreateView(@Nullable view: View, savedInstanceState: Bundle)
    fun onActivityCreate(@Nullable savedInstanceState: Bundle)
    fun onStart()
    fun onResume()
    fun onPause()
    fun onStop()
    fun onSaveInstanceState()
    fun onDestroyView()
    fun onDestroy()
    fun onDetach()
    fun isAdded(): Boolean

}