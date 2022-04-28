package com.project.donki.usecases

import com.project.core.domain.BaseRepository
import com.project.core.domain.CalendarRepository
import com.project.core.usecase.BaseUseCase
import com.project.donki.entities.SolarFlare

class FLRUseCase(calendarRepository: CalendarRepository, repository: BaseRepository<List<SolarFlare>>) :
    BaseUseCase<List<SolarFlare>>(calendarRepository, repository)