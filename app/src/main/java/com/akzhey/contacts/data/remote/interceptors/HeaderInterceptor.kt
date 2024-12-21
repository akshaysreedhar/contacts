package com.akzhey.contacts.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import com.akzhey.contacts.BuildConfig
import com.akzhey.contacts.common.Constants

/**
 * For setting up api key in header
 */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val modifiedRequest = request.newBuilder()
            .addHeader(Constants.API_KEY, BuildConfig.API_KEY)
            .build()
        return chain.proceed(modifiedRequest)
    }
}