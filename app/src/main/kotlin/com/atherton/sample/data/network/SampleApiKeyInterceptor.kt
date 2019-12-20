package com.atherton.sample.data.network

import okhttp3.Interceptor
import okhttp3.Response

class SampleApiKeyInterceptor(private val apiKey: String, private val apiHost: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()

        // only intercept requests that are going to apiHost
        return if (originalUrl.host() == apiHost) {
            val url = originalUrl.newBuilder()
                .addQueryParameter("api_key", apiKey)
                .build()

            val requestBuilder = originalRequest.newBuilder().url(url)
            return chain.proceed(requestBuilder.build())
        } else {
            chain.proceed(originalRequest)
        }
    }
}
