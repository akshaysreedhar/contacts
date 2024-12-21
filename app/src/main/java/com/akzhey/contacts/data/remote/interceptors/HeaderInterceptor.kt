package com.akzhey.contacts.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response

/**
 * For setting up api key in header
 */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val modifiedRequest = request.newBuilder()
            .addHeader("X-Api-Key", "68742b10dbc14eae8a19acd6511c2500")
            .build()
        return chain.proceed(modifiedRequest)
    }
}