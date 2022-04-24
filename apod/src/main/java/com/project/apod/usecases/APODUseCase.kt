package com.project.apod.usecases

import com.project.apod.domain.APODRepository
import com.project.apod.entities.APODResponse
import com.project.core.domain.CalendarRepository
import com.project.core.usecase.BaseUseCase

class APODUseCase(calendarRepository: CalendarRepository, apodRepository: APODRepository) :
    BaseUseCase<List<APODResponse>>(calendarRepository, apodRepository)