package com.ppjun.android.arms.integration

import android.os.Message
import org.simple.eventbus.EventBus

/**
 * Created by ppjun on 3/12/18.
 */
class AppManager {
    companion object {
        val APPMANAGER_MESSAGE = "appmanager_message"
        val SHOW_SNACKBAR = 5001
        val START_ACTIVITY = 5000
        val KILL_ALL = 5002
        val EXIT_APP = 5003

        fun post(msg: Message) {
            EventBus.getDefault().post(msg, APPMANAGER_MESSAGE)
        }
    }
}