package ru.arturvasilov.multimodulemovie.network

import okhttp3.Interceptor
import okhttp3.Response

internal class ApiKeyInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url().newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()
        val modifiedRequest = request.newBuilder().url(url).build()
        return chain.proceed(modifiedRequest)
    }
}