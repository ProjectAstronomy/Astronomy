package com.project.epic.usecases

import com.project.epic.domain.local.EPICRepositoryLocal
import com.project.epic.domain.remote.EPICRepository
import com.project.epic.entities.remote.EPICResponse

class EPICUseCase(
    private val remoteRepository: EPICRepository,
    private val localRepository: EPICRepositoryLocal
) {
    suspend fun load(isNetworkAvailable: Boolean, quality: String): List<EPICResponse> {
        return if (!isNetworkAvailable) {
            localRepository.getAll()
        } else {
            remoteRepository.loadAsync(quality)
        }
    }

    suspend fun insert(epicResponse: EPICResponse): Unit = localRepository.insert(epicResponse)
}