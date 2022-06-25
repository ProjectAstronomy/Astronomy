package com.project.core.domain

import com.project.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class BaseInterceptor private constructor() : Interceptor {
    companion object {
        val interceptor get() = BaseInterceptor()
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder().addQueryParameter("api_key", BuildConfig.NASA_API_KEY).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}