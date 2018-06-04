package com.ppjun.android.arms.utils

import android.Manifest
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.annotations.NonNull
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import timber.log.Timber
import java.util.*

/**
 * Created by ppjun on 3/12/18.
 */
class PermissionUtil {
    val TAG = "Permission"


    private constructor() {
        throw IllegalStateException("you can't instantiate me!")
    }

    interface RequestPermission {
        /**
         * 权限请求成功
         */
        fun onRequestPermissionSuccess()

        /**
         * 用户拒绝了权限请求, 权限请求失败, 但还可以继续请求该权限
         *
         * @param permissions 请求失败的权限名
         */
        fun onRequestPermissionFailure(permissions: List<String>)

        /**
         * 用户拒绝了权限请求并且用户选择了以后不再询问, 权限请求失败, 这时将不能继续请求该权限, 需要提示用户进入设置页面打开该权限
         *
         * @param permissions 请求失败的权限名
         */
        fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>)
    }


    fun requestPermission(requestPermission: RequestPermission, rxPermissions: RxPermissions, errorHandler: RxErrorHandler, vararg permissions: String) {
        if (permissions == null || permissions.size == 0) return

        val needRequest = ArrayList<String>()
        for (permission in permissions) { //过滤调已经申请过的权限
            if (!rxPermissions.isGranted(permission)) {
                needRequest.add(permission)
            }
        }

        if (needRequest.isEmpty()) {//全部权限都已经申请过，直接执行操作
            requestPermission.onRequestPermissionSuccess()
        } else {//没有申请过,则开始申请
            rxPermissions
                    .requestEach(*needRequest.toTypedArray())
                    .buffer(permissions.size)
                    .subscribe(object : ErrorHandleSubscriber<List<Permission>>(errorHandler) {
                        override fun onNext(@NonNull permissions: List<Permission>) {
                            for (p in permissions) {
                                if (!p.granted) {
                                    if (p.shouldShowRequestPermissionRationale) {
                                        Timber.tag(TAG).d("Request permissions failure")
                                        requestPermission.onRequestPermissionFailure(Arrays.asList(p.name))
                                        return
                                    } else {
                                        Timber.tag(TAG).d("Request permissions failure with ask never again")
                                        requestPermission.onRequestPermissionFailureWithAskNeverAgain(Arrays.asList(p.name))
                                        return
                                    }
                                }
                            }
                            Timber.tag(TAG).d("Request permissions success")
                            requestPermission.onRequestPermissionSuccess()
                        }
                    })
        }

    }


    /**
     * 请求摄像头权限
     */
    fun launchCamera(requestPermission: RequestPermission, rxPermissions: RxPermissions, errorHandler: RxErrorHandler) {
        requestPermission(requestPermission, rxPermissions, errorHandler, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    }


    /**
     * 请求外部存储的权限
     */
    fun externalStorage(requestPermission: RequestPermission, rxPermissions: RxPermissions, errorHandler: RxErrorHandler) {
        requestPermission(requestPermission, rxPermissions, errorHandler, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }


    /**
     * 请求发送短信权限
     */
    fun sendSms(requestPermission: RequestPermission, rxPermissions: RxPermissions, errorHandler: RxErrorHandler) {
        requestPermission(requestPermission, rxPermissions, errorHandler, Manifest.permission.SEND_SMS)
    }


    /**
     * 请求打电话权限
     */
    fun callPhone(requestPermission: RequestPermission, rxPermissions: RxPermissions, errorHandler: RxErrorHandler) {
        requestPermission(requestPermission, rxPermissions, errorHandler, Manifest.permission.CALL_PHONE)
    }


    /**
     * 请求获取手机状态的权限
     */
    fun readPhonestate(requestPermission: RequestPermission, rxPermissions: RxPermissions, errorHandler: RxErrorHandler) {
        requestPermission(requestPermission, rxPermissions, errorHandler, Manifest.permission.READ_PHONE_STATE)
    }

}