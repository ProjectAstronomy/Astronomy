package com.project.mrp.domain

import com.project.mrp.entities.Photos
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotosApiService {
    @GET("/mars-photos/api/v1/rovers/{rover_name}")
    suspend fun loadPhotosByMartianSol(
        @Path("rover_name") roverName: String,
        @Query("sol") sol: Long
    ): Photos
}