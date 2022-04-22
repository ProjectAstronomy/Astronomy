package com.project.donki.usecase

import com.project.core.domain.CalendarRepository
import com.project.core.usecase.BaseUseCase
import com.project.donki.domain.SolarFlareRepository
import com.project.donki.entities.SolarFlareResponse

class SolarFlareUseCase(
    calendarRepository: CalendarRepository,
    solarFlareRepository: SolarFlareRepository
) : BaseUseCase<List<SolarFlareResponse>>(calendarRepository, solarFlareRepository)