package com.project.apod.domain

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.project.apod.BuildConfig
import com.project.apod.entities.APODResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WebDataSource {
    companion object {
        private const val BASE_URL_LOCATIONS = "https://api.nasa.gov/"
    }

    private val apodApiService = Retrofit.Builder()
        .baseUrl(BASE_URL_LOCATIONS)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(OkHttpClient.Builder().apply {
            addInterceptor(BaseInterceptor.interceptor)
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }.build())
        .build()
        .create(APODApiService::class.java)

    suspend fun getAPODByDate(date: String): retrofit2.Response<APODResponse> =
        apodApiService.getAPODByDate(date, BuildConfig.DEMO_KEY)

    suspend fun getAPODFromDateToDate(startDate: String, endDate: String): retrofit2.Response<List<APODResponse>> =
        apodApiService.getAPODFromDateToDate(startDate, endDate, BuildConfig.DEMO_KEY)

    suspend fun getAPODByCount(count: Int): retrofit2.Response<List<APODResponse>> =
        apodApiService.getAPODByCount(count, BuildConfig.DEMO_KEY)

    class BaseInterceptor private constructor() : Interceptor {
        companion object {
            val interceptor: BaseInterceptor
                get() = BaseInterceptor()
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