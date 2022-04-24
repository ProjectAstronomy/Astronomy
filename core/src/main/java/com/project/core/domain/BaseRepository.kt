package com.project.core.domain

interface BaseRepository<T> {
    suspend fun loadAsync(startDate: String, endDate: String): T
}