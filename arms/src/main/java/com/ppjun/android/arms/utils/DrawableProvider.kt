package com.ppjun.android.arms.utils

import android.graphics.Bitmap
import android.graphics.Matrix

/**
 * Created by ppjun on 3/12/18.
 */
class DrawableProvider {
    companion object {

        /**
         * 改变Bitmap的长宽
         *
         * @param bitmap
         * @return
         */
        fun getResizeBitmap(bitmap: Bitmap, targetWidth: Float, targetHeight: Float): Bitmap {
            var width = bitmap.width
            var height = bitmap.height
            var matrix = Matrix()
            matrix.postScale(targetWidth / width, targetHeight / height)
            var returnBm: Bitmap? = null
            try {
                returnBm = Bitmap.createBitmap(bitmap, 0, 0, width,
                        height, matrix
                        , true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if (returnBm == null) {
                returnBm = bitmap
            }
            if (bitmap != null) {
                bitmap.recycle()
            }
            return returnBm
        }
    }
}