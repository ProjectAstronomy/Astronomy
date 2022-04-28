package com.project.mrp.domain

import com.project.mrp.entities.Camera
import com.project.mrp.entities.Photo
import com.project.mrp.entities.Photos
import com.project.mrp.entities.Rover

class PhotosRepositoryFake : BasePhotosRepository {
    override suspend fun loadPhotosByMartianSol(roverName: String, sol: Long): Photos {
        val list = mutableListOf<Photo>()

        repeat(100) {
            list.add(
                Photo(
                    camera = Camera(
                        fullName = "fullName",
                        id = Long.MAX_VALUE,
                        name = "name",
                        roverId = Long.MAX_VALUE
                    ),
                    earthDate = "earthDate",
                    id = it.toLong(),
                    imgSrc = "imgSrc",
                    rover = Rover(
                        id = Long.MAX_VALUE,
                        landingDate = "landingDate",
                        launchDate = "launchDate",
                        name = "name",
                        status = "status"
                    ),
                    sol = Long.MAX_VALUE
                )
            )
        }

        return Photos(photos = list)
    }
}