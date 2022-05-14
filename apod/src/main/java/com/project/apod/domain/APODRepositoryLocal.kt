package com.project.apod.domain

import com.project.apod.entities.APODEntity
import com.project.apod.entities.APODResponse

class APODRepositoryLocal(private val dao: APODDao) {
    suspend fun insert(apodResponse: APODResponse): Unit = dao.insert(convert(apodResponse))

    suspend fun getAll(): List<APODResponse> = dao.getAll().map { convert(it) }

    private fun convert(apodResponse: APODResponse): APODEntity =
        APODEntity(
            copyright = apodResponse.copyright,
            date = apodResponse.date,
            explanation = apodResponse.explanation,
            hdurl = apodResponse.hdurl,
            mediaType = apodResponse.mediaType,
            serviceVersion = apodResponse.serviceVersion,
            title = apodResponse.title,
            url = apodResponse.url
        )

    private fun convert(apodEntity: APODEntity): APODResponse =
        APODResponse(
            copyright = apodEntity.copyright,
            date = apodEntity.date,
            explanation = apodEntity.explanation,
            hdurl = apodEntity.hdurl,
            mediaType = apodEntity.mediaType,
            serviceVersion = apodEntity.serviceVersion,
            title = apodEntity.title,
            url = apodEntity.url
        )
}