package com.project.apod.domain

import com.project.apod.entities.APODResponse
import com.project.core.domain.BaseRepository

class APODRepositoryFake : BaseRepository<List<APODResponse>> {
    override suspend fun loadAsync(startDate: String, endDate: String): List<APODResponse> {
        val list = mutableListOf<APODResponse>()

        repeat(100) {
            list.add(
                APODResponse(
                    "copyright",
                    "$it:$it:$it",
                    "explanation",
                    "hdurl",
                    "mediaType",
                    "serviceVersion",
                    "title",
                    "url"
                )
            )
        }

        return list
    }
}