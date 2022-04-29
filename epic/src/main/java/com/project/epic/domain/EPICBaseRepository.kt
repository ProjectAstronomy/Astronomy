package com.project.epic.domain

import com.project.epic.entities.EPICResponse

interface EPICBaseRepository {
    suspend fun loadAsync(quality: String = "natural"): List<EPICResponse>
}