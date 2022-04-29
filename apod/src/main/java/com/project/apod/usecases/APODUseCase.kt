package com.project.apod.usecases

import com.project.apod.entities.APODResponse
import com.project.core.domain.BaseRepository
import com.project.core.domain.CalendarRepository
import com.project.core.usecase.BaseUseCase

class APODUseCase(calendarRepository: CalendarRepository, repository: BaseRepository<List<APODResponse>>) :
    BaseUseCase<List<APODResponse>>(calendarRepository, repository)