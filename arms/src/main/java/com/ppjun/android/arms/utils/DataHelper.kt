package com.ppjun.android.arms.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Environment
import java.io.File

/**
 * Created by ppjun on 3/12/18.
 */
class DataHelper {

    private constructor() {
        throw IllegalStateException("you can't instantiate me!")
    }

    companion object {
        fun getCacheFile(context: Context): File {
            if (Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)) {
                var file = context.externalCacheDir
                if (file == null) {
                    file = File(getCacheFilePath(context))
                    makeDirs(file)
                }
                return file

            } else {
                return context.cacheDir
            }
        }

        private fun getCacheFilePath(context: Context): String {
            var packName = context.packageName
            return Environment.getExternalStorageDirectory().path + packName

        }

        private fun makeDirs(file: File): File {
            if (!file.exists()) {
                file.mkdir()
            }
            return file
        }

    }


}