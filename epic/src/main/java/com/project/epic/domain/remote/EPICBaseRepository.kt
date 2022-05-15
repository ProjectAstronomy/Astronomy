package com.project.epic.domain.remote

import com.project.epic.entities.remote.EPICResponse

interface EPICBaseRepository {
    suspend fun loadAsync(quality: String = "natural"): List<EPICResponse>
}