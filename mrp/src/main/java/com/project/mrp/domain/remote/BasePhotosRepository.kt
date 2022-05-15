package com.project.mrp.domain.remote

import com.project.mrp.entities.remote.Photos

interface BasePhotosRepository {
    suspend fun loadPhotosByMartianSol(roverName: String, sol: Long): Photos
}