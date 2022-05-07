package com.project.epic.domain

import com.project.epic.entities.EPICResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface EPICApiService {
    @GET("EPIC/api/{quality}/images")
    suspend fun loadEPIC(@Path("quality") quality: String): List<EPICResponse>
}