package com.project.epic.domain.local

import com.project.epic.entities.local.EPICEntity
import com.project.epic.entities.remote.EPICResponse

class EPICRepositoryLocal(private val dao: EPICDao) {
    suspend fun insert(epicResponse: EPICResponse): Unit = dao.insert(convert(epicResponse))

    suspend fun getAll(): List<EPICResponse> = dao.getAll().map { convert(it) }

    private fun convert(epicResponse: EPICResponse): EPICEntity =
        EPICEntity(
            identifier = epicResponse.identifier,
            caption = epicResponse.caption,
            image = epicResponse.image,
            version = epicResponse.version,
            date = epicResponse.date,
            attitudeQuaternions = epicResponse.attitudeQuaternions,
            centroidCoordinates = epicResponse.centroidCoordinates,
            dscovrJ2000Position = epicResponse.dscovrJ2000Position,
            lunarJ2000Position = epicResponse.lunarJ2000Position,
            sunJ2000Position = epicResponse.sunJ2000Position
        )

    private fun convert(epicEntity: EPICEntity): EPICResponse =
        EPICResponse(
            identifier = epicEntity.identifier,
            caption = epicEntity.caption,
            image = epicEntity.image,
            version = epicEntity.version,
            centroidCoordinates = epicEntity.centroidCoordinates,
            dscovrJ2000Position = epicEntity.dscovrJ2000Position,
            lunarJ2000Position = epicEntity.lunarJ2000Position,
            sunJ2000Position = epicEntity.sunJ2000Position,
            attitudeQuaternions = epicEntity.attitudeQuaternions,
            date = epicEntity.date
        )
}