package com.project.epic.domain

import com.project.epic.entities.EPICResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EPICApiKey {
    @GET("EPIC/api/{quality}/images")
    suspend fun loadEPIC(
        @Path("quality") quality: String,
        @Query("api_key") apiKey: String
    ): List<EPICResponse>
}