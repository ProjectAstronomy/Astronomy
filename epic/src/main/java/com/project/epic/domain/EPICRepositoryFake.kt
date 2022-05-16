package com.project.epic.domain

import com.project.epic.entities.AttitudeQuaternions
import com.project.epic.entities.CentroidCoordinates
import com.project.epic.entities.EPICResponse
import com.project.epic.entities.J2000Position
import okhttp3.ResponseBody
import retrofit2.Call

class EPICRepositoryFake : EPICBaseRepository {
    override suspend fun loadAsync(quality: String): List<EPICResponse> {
        val list = mutableListOf<EPICResponse>()
        for (i in 0..99) {
            val position = J2000Position(x = i.toDouble(), y = i.toDouble(), z = i.toDouble())
            list.add(
                EPICResponse(
                    identifier = "$i",
                    caption = "caption",
                    image = "image",
                    version = "version",
                    centroidCoordinates = CentroidCoordinates(lat = i.toDouble(), lon = i.toDouble()),
                    dscovrJ2000Position = position,
                    lunarJ2000Position = position,
                    sunJ2000Position = position,
                    attitudeQuaternions = AttitudeQuaternions(
                        q0 = i.toDouble(),
                        q1 = i.toDouble(),
                        q2 = i.toDouble(),
                        q3 = i.toDouble()
                    ),
                    date = "date"
                )
            )
        }
        return list
    }
}