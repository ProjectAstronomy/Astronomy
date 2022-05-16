package com.project.mrp.entities.local

import androidx.room.Embedded
import androidx.room.Relation

data class ManifestWithPhotos(
    @Embedded val photoManifestEntity: PhotoManifestEntity,
    @Relation(
        parentColumn = "name",
        entityColumn = "photo_manifest_name"
    )
    val photosInformation: List<PhotosInformationEntity>
)