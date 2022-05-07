package com.project.epic.domain

import com.project.epic.entities.EPICResponse

class EPICRepository(private val epicApiService: EPICApiService) : EPICBaseRepository {
    override suspend fun loadAsync(quality: String): List<EPICResponse> =
        epicApiService.loadEPIC(quality)
}