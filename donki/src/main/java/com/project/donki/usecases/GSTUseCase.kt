package com.project.donki.usecases

import com.project.core.domain.BaseRepository
import com.project.core.domain.CalendarRepository
import com.project.core.usecase.BaseUseCase
import com.project.donki.entities.GeomagneticStorm

class GSTUseCase(calendarRepository: CalendarRepository, repository: BaseRepository<List<GeomagneticStorm>>) :
    BaseUseCase<List<GeomagneticStorm>>(calendarRepository, repository)