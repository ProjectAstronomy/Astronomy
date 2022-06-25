package com.project.mrp

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.project.core.BuildConfig
import com.project.core.domain.BaseInterceptor
import com.project.mrp.domain.remote.api.PhotosApiService
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PhotosApiServiceTest {
    private val roverNames = listOf("Spirit", "Opportunity", "Curiosity")
    private val sol: Long = 1000
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
            .create(PhotosApiService::class.java)
    }

    @Test
    fun testRequest(): Unit = runBlocking {
        retrofitBuilder
            .client(OkHttpClient.Builder().apply {
                addInterceptor { chain: Interceptor.Chain ->
                    var request = chain.request()
                    val url = request.url.newBuilder().addQueryParameter("api_key", BuildConfig.NASA_API_KEY).build()
                    request = request.newBuilder().url(url).build()
                    assertEquals("path", listOf("mars-photos", "api", "v1", "rovers", roverNames[0]), url.pathSegments)
                    assertEquals("first query parameter", "sol", url.queryParameterName(0))
                    assertEquals("second query parameter", "api_key", url.queryParameterName(1))
                    chain.proceed(request)
                }
            }.build())
            .build()
            .create(PhotosApiService::class.java)
            .loadPhotosByMartianSol(roverNames[0], sol)
    }
}