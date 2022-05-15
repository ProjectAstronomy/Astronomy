package com.project.mrp

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.project.core.BuildConfig
import com.project.core.domain.BaseInterceptor
import com.project.mrp.domain.remote.MissionManifestApiService
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MissionManifestApiServiceTest {
    private val roverNames = listOf("Spirit", "Opportunity", "Curiosity")
    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .validateEagerly(true)

    @Test
    fun testValidateInterface() {
        retrofitBuilder
            .client(OkHttpClient.Builder().apply {
                addInterceptor(BaseInterceptor.interceptor)
            }.build())
            .build()
            .create(MissionManifestApiService::class.java)
    }

    @Test
    fun testRequest(): Unit = runBlocking {
        retrofitBuilder
            .client(OkHttpClient.Builder().apply {
                addInterceptor { chain: Interceptor.Chain ->
                    var request = chain.request()
                    val url = request.url.newBuilder().addQueryParameter("api_key", BuildConfig.NASA_API_KEY).build()
                    request = request.newBuilder().url(url).build()
                    assertEquals("path", listOf("mars-photos", "api", "v1", "manifests", roverNames[0]), url.pathSegments)
                    chain.proceed(request)
                }
            }.build())
            .build()
            .create(MissionManifestApiService::class.java)
            .loadMissionManifest(roverNames[0])
    }
}