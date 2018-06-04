package com.ppjun.android.arms.http.log

import android.support.annotation.Nullable
import com.ppjun.android.arms.http.GlobalHttpHandler
import com.ppjun.android.arms.utils.CharacterHandler
import com.ppjun.android.arms.utils.ZipHelper
import okhttp3.*
import okio.Buffer
import timber.log.Timber
import java.io.IOException
import java.net.URLDecoder
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ppjun on 3/9/18.
 */

@Singleton
class RequestInterceptor : Interceptor {
    @Inject
    @Nullable
    lateinit var mHandler: GlobalHttpHandler

    @Inject
    lateinit var mPrinter: FormatPrinter
    @Inject
    lateinit var printLevel: Level



       enum class Level {
           NONE,
           REQUEST,
           RESPONSE,
           ALL


   }





    @Inject
    constructor()

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        var logRequest = printLevel == RequestInterceptor.Level.ALL || (printLevel != Level.ALL && printLevel == Level.REQUEST)
        if (logRequest) {
            if (request.body() != null && isParseable(request!!.body()!!.contentType()!!)) {
                mPrinter.printJsonRequest(request, parseParams(request))
            } else {
                mPrinter.printFileRequest(request)

            }


        }
        var logResponse = printLevel == Level.ALL || (printLevel != Level.ALL && printLevel == Level.RESPONSE)

        var t1 = if (logResponse) System.nanoTime() else 0

        var originalResponse: Response? = null
        try {
            originalResponse = chain.proceed(request)

        } catch (e: Exception) {
            Timber.w("http error" + e)
            throw e
        }
        var t2 = if (logResponse) System.nanoTime() else 0
        var responseBody = originalResponse.body()

        var bodyString = ""
        if (responseBody != null && isParseable(responseBody.contentType()!!)) {
            bodyString = printResult(request, originalResponse, logResponse)
        }
        if (logResponse) {
            val segmentList = request.url().encodedPathSegments()
            val header = originalResponse.headers().toString()
            val code = originalResponse.code()
            val isSuccessful = originalResponse.isSuccessful
            val message = originalResponse.message()
            val url = originalResponse.request().url().toString()
            if (isParseable(responseBody?.contentType()!!)) {
                mPrinter.printJsonResponse(TimeUnit.NANOSECONDS.toMillis(t2 - t1),
                        isSuccessful,
                        code,
                        header,
                        responseBody.contentType()!!,
                        bodyString,
                        segmentList,
                        message,
                        url
                )
            } else {
                mPrinter.printFileResponse(TimeUnit.NANOSECONDS.toMillis(t2 - t1),
                        isSuccessful,
                        code,
                        header,
                        segmentList,
                        message,
                        url
                )
            }
        }
        return mHandler!!.onHttpResultResponse(bodyString, chain, originalResponse)


        return originalResponse


    }


    @Throws(IOException::class)
    fun printResult(request: Request, response: Response, logResponse: Boolean): String {
        var responseBody: ResponseBody
        var buffer: Buffer
        var encoding: String
        var clone: Buffer
        try {
            responseBody = response.newBuilder().build().body()!!
            var source = responseBody.source()
            source.request(Long.MAX_VALUE)
            buffer = source.buffer()
            encoding = response.headers()
                    .get("Content-Encoding")!!
            clone = buffer.clone()
        } catch (e: Exception) {
            e.printStackTrace()
            return "{\"error\":\"" + e.message + "\"}"
        }

        return parseContent(responseBody, encoding, clone)
    }

    /**
     * 解析服务器响应的内容
     *
     * @param responseBody
     * @param encoding
     * @param clone
     * @return
     */
    private fun parseContent(responseBody: ResponseBody, encoding: String, clone: Buffer): String {
        val charset = Charset.forName("UTF-8")
        val mediaType = responseBody.contentType()
        mediaType?.charset(charset)
        if (encoding.equals("gzip", true)) {
            return ZipHelper.decompressForGzip(clone.readByteArray(), converCharset(charset))
        } else if (encoding.equals("zlib", true)) {
            return ZipHelper.decompressToStringForZlib(clone.readByteArray(), converCharset(charset))
        } else {
            return clone.readString(charset)
        }
    }

    fun parseParams(request: Request): String {
        try {
            val body: RequestBody? = request.newBuilder().build().body() ?: return ""
            val buffer = Buffer()
            body?.writeTo(buffer)
            var charset = Charset.forName("UTF-8")
            val mediaType = body?.contentType()
            charset = mediaType?.charset(charset)
            return CharacterHandler.jsonFormat(URLDecoder.decode(buffer.readString(charset), converCharset(charset)))
        } catch (e: Exception) {
            return "{\"error\":\"" + e.message + "\"}"
        }

    }

    fun isParseable(mediaType: MediaType): Boolean {
        return isText(mediaType) || isPlain(mediaType)
                || isJson(mediaType) || isForm(mediaType)
                || isHtml(mediaType) || isXml(mediaType)
    }

    private fun isXml(mediaType: MediaType): Boolean {
        if (mediaType.type() == null) return false
        return mediaType.type().toLowerCase().contains("xml")
    }

    private fun isHtml(mediaType: MediaType): Boolean {

        if (mediaType.type() == null) return false
        return mediaType.type().toLowerCase().contains("html")
    }

    private fun isText(mediaType: MediaType): Boolean {
        if (mediaType.type() == null) return false
        return mediaType.type() == "text"

    }

    fun isPlain(mediaType: MediaType): Boolean {
        if (mediaType.type() == null) return false
        return mediaType.type().toLowerCase().contains("plain")
    }

    fun isJson(mediaType: MediaType): Boolean {
        if (mediaType.type() == null) return false
        return mediaType.type().toLowerCase().contains("json")
    }

    fun isForm(mediaType: MediaType): Boolean {
        if (mediaType.type() == null) return false
        return mediaType.type().toLowerCase().contains("x-www-form-urlencoded")
    }

    fun converCharset(charset: Charset): String {
        val s = charset.toString()
        val i = s.indexOf("[")
        if (i == -1) {
            return s
        }
        return s.substring(i + 1, s.length - 1)

    }

}