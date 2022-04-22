package com.project.donki.usecase

import com.project.core.domain.CalendarRepository
import com.project.core.usecase.BaseUseCase
import com.project.donki.domain.FLRRepository
import com.project.donki.entities.SolarFlare

class FLRUseCase(
    calendarRepository: CalendarRepository,
    flrRepository: FLRRepository
) : BaseUseCase<List<SolarFlare>>(calendarRepository, flrRepository)