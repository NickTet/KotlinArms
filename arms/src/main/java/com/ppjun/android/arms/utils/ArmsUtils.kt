package com.ppjun.android.arms.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Message
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.ppjun.android.arms.base.App
import com.ppjun.android.arms.di.component.AppComponent
import com.ppjun.android.arms.integration.AppManager
import com.ppjun.android.arms.integration.AppManager.Companion.EXIT_APP
import com.ppjun.android.arms.integration.AppManager.Companion.KILL_ALL
import com.ppjun.android.arms.integration.AppManager.Companion.SHOW_SNACKBAR
import com.ppjun.android.arms.integration.AppManager.Companion.START_ACTIVITY

/**
 * Created by ppjun on 3/7/18.
 */
class ArmsUtils {
    private constructor() {
        throw IllegalStateException("you can't instantiate me!")
    }

    companion object {


        lateinit var mToast: Toast

        /**
         * 设置hint大小
         *
         * @param size
         * @param v
         * @param res
         */
        fun setViewHintSize(context: Context, size: Int, v: TextView, res: Int) {
            var ss = SpannableString(getResource(context).getString(res))
            var sizeSpan = AbsoluteSizeSpan(size, true)
            ss.setSpan(sizeSpan, 0, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            v.hint = SpannableString(ss)
        }

        /**
         * dip转pix
         *
         * @param dpValue
         * @return
         */
        fun dip2px(context: Context, dpValue: Float): Int {
            val scale = getResource(context).displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }


        /**
         * 获得资源
         */
        fun getResource(context: Context): Resources {
            return context.resources
        }

        /**
         * 得到字符数组
         */
        fun getStringArray(context: Context, res: Int): Array<String> {
            return getResource(context).getStringArray(res)
        }

        /**
         * pix转dip
         */
        fun pix2dip(context: Context, pxValue: Float): Int {
            val densityDpi = getResource(context).displayMetrics.density
            return (pxValue / densityDpi + 0.5f).toInt()
        }

        /**
         * 从 dimens 中获得尺寸
         *
         * @param context
         * @param id
         * @return
         */
        fun getDimens(context: Context, id: Int): Int {
            return getResource(context).getDimension(id).toInt()
        }

        /**
         * 从String 中获得字符
         *
         * @return
         */
        fun getString(context: Context, id: Int): String {
            return getResource(context).getString(id)
        }

        fun snackbarText(text: String) {
            val message = Message();
            message.what = SHOW_SNACKBAR
            message.obj = text
            message.arg1 = 0
            AppManager.post(message)
        }

        /**
         * 跳转界面 2 ,通过 {@link AppManager#startActivity(Intent)}
         *
         * @param
         */
        fun startActivity(intent: Intent) {
            val message = Message()
            message.what = START_ACTIVITY
            message.obj = intent
            AppManager.post(message)
        }


        fun getColor(context: Context, id: Int): Int {
            return getResource(context).getColor(id)
        }

        /**
         * 全屏,并且沉侵式状态栏
         *
         * @param activity
         */
        fun statusInScreen(activity: Activity) {

            var attrs = activity.window.attributes
            attrs.flags = attrs.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN.inv()
            activity.window.attributes = attrs
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        }

        /**
         * 配置 RecyclerView
         *
         * @param recyclerView
         * @param layoutManager
         */
        fun configRecyclerView(recyclerView: RecyclerView, layoutManager: RecyclerView.LayoutManager) {
            recyclerView.layoutManager = layoutManager
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = DefaultItemAnimator()

        }

        /**
         * 远程遥控 {@link AppManager#killAll()}
         */
        fun killAll() {
            val message = Message()
            message.what = KILL_ALL
            AppManager.post(message)
        }

        fun exitApp() {
            val message = Message()
            message.what = EXIT_APP
            AppManager.post(message)
        }

        fun obtainAppComponentFromContext(context: Context): AppComponent {
            Preconditions.checkNotNull(context.applicationContext,
                    "%s can not be null",
                    Context::class.java.name)
            Preconditions.checkState(context.applicationContext is App, "Application does not implements App")
            return (context.applicationContext as App).getAppComponent()
        }
    }

}