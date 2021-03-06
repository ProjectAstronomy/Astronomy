package com.project.epic.domain.remote

import com.project.epic.entities.remote.EPICResponse

class EPICRepository(private val epicApiService: EPICApiService) {
    suspend fun loadAsync(quality: String): List<EPICResponse> = epicApiService.loadEPIC(quality)
}