package com.project.donki.usecase

import com.project.core.domain.CalendarRepository
import com.project.core.usecase.BaseUseCase
import com.project.donki.domain.GSTRepository
import com.project.donki.entities.GeomagneticStorm

class GSTUseCase(calendarRepository: CalendarRepository, gstRepository: GSTRepository) :
    BaseUseCase<List<GeomagneticStorm>>(calendarRepository, gstRepository)