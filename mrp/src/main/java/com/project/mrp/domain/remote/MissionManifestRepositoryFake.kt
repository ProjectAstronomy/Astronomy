package com.project.mrp.domain.remote

import com.project.mrp.entities.remote.MissionManifest
import com.project.mrp.entities.remote.PhotoManifest
import com.project.mrp.entities.remote.PhotosInformation

class MissionManifestRepositoryFake : BaseMissionManifestRepository {
    override suspend fun loadMissionManifest(roverName: String): MissionManifest {
        val list = mutableListOf<PhotosInformation>()

        repeat(100) {
            list.add(
                PhotosInformation(
                    sol = it.toLong(),
                    earthDate = "earthDate",
                    totalPhotos = Long.MAX_VALUE,
                    cameras = listOf("CHEMCAM", "FHAZ", "MARDI", "RHAZ")
                )
            )
        }

        return MissionManifest(
            PhotoManifest(
                landingDate = "landingDate",
                launchDate = "launchDate",
                maxDate = "maxDate",
                maxSol = Long.MAX_VALUE,
                name = "name",
                status = "status",
                totalPhotos = Long.MAX_VALUE,
                photos = list
            )
        )
    }
}