package com.project.epic.domain

import com.project.epic.BuildConfig
import com.project.epic.entities.EPICResponse

class EPICRepository(private val epicApiKey: EPICApiKey) {
    suspend fun loadAsync(quality: String = "natural"): List<EPICResponse> =
        epicApiKey.loadEPIC(quality, BuildConfig.NASA_API_KEY)
}