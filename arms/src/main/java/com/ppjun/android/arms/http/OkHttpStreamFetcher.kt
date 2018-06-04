package com.ppjun.android.arms.http

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.HttpException
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.util.ContentLengthInputStream
import com.bumptech.glide.util.Synthetic
import okhttp3.*
import java.io.IOException
import java.io.InputStream

/**
 * Created by ppjun on 3/14/18.
 */
class OkHttpStreamFetcher : DataFetcher<InputStream>,
        okhttp3.Callback {


    val TAG = "OkHttpStreamFetcher"
    var client: Call.Factory
    var url: GlideUrl
    @Synthetic
    var stream: InputStream? = null
    @Synthetic
    var responseBody: ResponseBody? = null
    @Volatile lateinit var call: Call
    var callback: DataFetcher.DataCallback<in InputStream>? = null


    constructor(client: Call.Factory, url: GlideUrl) {
        this.client = client
        this.url = url
    }

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>) {
        var requestBuilder = Request.Builder().url(url.toStringUrl())




        for (headerEntry in url.headers.entries) {
            val key = headerEntry.key
            requestBuilder.addHeader(key, headerEntry.value)
        }
        var request = requestBuilder.build()
        this.callback = callback
        call = client.newCall(request)
        //不是oreo就异步
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            call.enqueue(this)
        } else {
            try {
                // Calling execute instead of enqueue is a workaround for #2355, where okhttp throws a
                // ClassCastException on O.
                onResponse(call, call.execute())
            } catch (e: IOException) {
                onFailure(call, e)
            } catch (e: ClassCastException) {
                // It's not clear that this catch is necessary, the error may only occur even on O if
                // enqueue is used.
                onFailure(call, IOException("Workaround for framework bug on O"))
            }
        }
    }

    override fun getDataClass() = InputStream::class.java

    override fun cleanup() {
        stream?.close()
        responseBody?.close()
        callback = null
    }

    override fun cancel() {
        var local = call
        local.cancel()


    }

    override fun getDataSource() = DataSource.REMOTE


    override fun onFailure(call: Call?, e: IOException?) {
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "okhttp failed to obtain result", e)

        }
        callback?.onLoadFailed(e!!)

    }

    @Throws(IOException::class)
    override fun onResponse(call: Call?, response: Response?) {
        responseBody = response?.body()!!
        if (response.isSuccessful) {
            var contentLength = responseBody?.contentLength();
            stream = ContentLengthInputStream.obtain(responseBody?.byteStream()!!, contentLength!!)
            callback?.onDataReady(stream)
        } else {
            callback?.onLoadFailed(HttpException(response.message(), response.code()))
        }
    }
}