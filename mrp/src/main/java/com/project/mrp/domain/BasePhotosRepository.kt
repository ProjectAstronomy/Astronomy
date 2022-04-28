package com.project.mrp.domain

import com.project.mrp.entities.Photos

interface BasePhotosRepository {
    suspend fun loadPhotosByMartianSol(roverName: String, sol: Long): Photos
}