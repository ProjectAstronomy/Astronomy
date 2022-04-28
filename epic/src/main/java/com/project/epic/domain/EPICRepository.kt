package com.project.epic.domain

import com.project.epic.entities.EPICResponse

class EPICRepository(private val epicApiKey: EPICApiKey) : EPICBaseRepository {
    override suspend fun loadAsync(quality: String): List<EPICResponse> =
        epicApiKey.loadEPIC(quality)
}