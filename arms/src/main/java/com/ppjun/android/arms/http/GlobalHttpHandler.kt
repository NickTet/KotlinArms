package com.ppjun.android.arms.http

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by ppjun on 3/7/18.
 */
interface GlobalHttpHandler {
    fun onHttpResultResponse(httpResult: String, chain: Interceptor.Chain, response: Response): Response
    fun onHttpRequestBefore(chain: Interceptor.Chain, request: Request): Request

    companion object {

        val EMPTY: GlobalHttpHandler = object : GlobalHttpHandler {
            override fun onHttpResultResponse(httpResult: String, chain: Interceptor.Chain, response: Response): Response {
                return response

            }

            override fun onHttpRequestBefore(chain: Interceptor.Chain, request: Request): Request {
                return request
            }


        }
    }


}