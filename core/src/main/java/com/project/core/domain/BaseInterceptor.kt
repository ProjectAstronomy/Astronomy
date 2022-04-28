package com.project.core.domain

import com.project.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class BaseInterceptor private constructor() : Interceptor {
    companion object {
        val interceptor get() = BaseInterceptor()
    }

    enum class ServerResponseStatusCode {
        INFO,
        SUCCESS,
        REDIRECTION,
        CLIENT_ERROR,
        SERVER_ERROR,
        UNDEFINED_ERROR
    }

    private var responseCode: Int = 0

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder().addQueryParameter("api_key", BuildConfig.NASA_API_KEY).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request).also { responseCode = it.code }
    }

    fun getResponseCode(): ServerResponseStatusCode {
        return when (responseCode / 100) {
            1 -> ServerResponseStatusCode.INFO
            2 -> ServerResponseStatusCode.SUCCESS
            3 -> ServerResponseStatusCode.REDIRECTION
            4 -> ServerResponseStatusCode.CLIENT_ERROR
            5 -> ServerResponseStatusCode.SERVER_ERROR
            else -> ServerResponseStatusCode.UNDEFINED_ERROR
        }
    }
}