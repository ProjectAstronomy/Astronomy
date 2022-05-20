package com.project.epic

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.project.core.BuildConfig
import com.project.core.domain.BaseInterceptor
import com.project.epic.domain.remote.EPICApiService
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Test

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EPICApiServiceTest {
    private val qualityType = listOf("natural", "enhanced")
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
            .create(EPICApiService::class.java)
    }

    @Test
    fun testRequest(): Unit = runBlocking {
        retrofitBuilder
            .client(OkHttpClient.Builder().apply {
                addInterceptor { chain: Interceptor.Chain ->
                    var request = chain.request()
                    val url = request.url.newBuilder().addQueryParameter("api_key", BuildConfig.NASA_API_KEY).build()
                    request = request.newBuilder().url(url).build()
                    assertEquals("path", listOf("EPIC", "api", qualityType[1], "images"), url.pathSegments)
                    chain.proceed(request)
                }
            }.build())
            .build()
            .create(EPICApiService::class.java)
            .loadEPIC(qualityType[1])
    }
}