package com.project.core.domain

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseWebDataSource {
    companion object {
        private const val BASE_URL_LOCATIONS = "https://api.nasa.gov/"
    }

    protected val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_LOCATIONS)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(OkHttpClient.Builder().apply {
            addInterceptor(BaseInterceptor.interceptor)
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }.build())
        .build()

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
            return chain.proceed(chain.request()).also { responseCode = it.code }
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
}