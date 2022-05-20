package com.project.apod

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.project.apod.domain.remote.APODApiService
import com.project.core.BuildConfig
import com.project.core.domain.BaseInterceptor
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APODApiServiceTest {
    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .validateEagerly(true)

    @Test
    fun testValidateInterface() {
        retrofitBuilder
            .client(OkHttpClient.Builder().apply {
                addInterceptor(BaseInterceptor.interceptor)
            }.build())
            .build()
            .create(APODApiService::class.java)
    }

    @Test
    fun testRequest(): Unit = runBlocking {
        retrofitBuilder
            .client(OkHttpClient.Builder().apply {
                addInterceptor { chain: Interceptor.Chain ->
                    var request = chain.request()
                    val url = request.url.newBuilder().addQueryParameter("api_key", BuildConfig.NASA_API_KEY).build()
                    request = request.newBuilder().url(url).build()
                    assertEquals("first query parameter", "start_date", url.queryParameterName(0))
                    assertEquals("second query parameter", "end_date", url.queryParameterName(1))
                    assertEquals("third query parameter", "api_key", url.queryParameterName(2))
                    chain.proceed(request)
                }
            }.build())
            .build()
            .create(APODApiService::class.java)
            .getAPODFromDateToDate("2022-4-6", "2022-5-6")
    }
}