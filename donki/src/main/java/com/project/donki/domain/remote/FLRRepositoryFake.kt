package com.project.donki.domain.remote

import com.project.core.domain.BaseRepository
import com.project.donki.entities.remote.Instrument
import com.project.donki.entities.remote.LinkedEvent
import com.project.donki.entities.remote.SolarFlare

class FLRRepositoryFake : BaseRepository<List<SolarFlare>> {
    override suspend fun loadAsync(startDate: String, endDate: String): List<SolarFlare> {
        val list = mutableListOf<SolarFlare>()
        val linkedEventsList = mutableListOf<LinkedEvent>()
        val instrumentsList = mutableListOf<Instrument>()

        repeat(4) { linkedEventsList.add(LinkedEvent("$it")) }

        repeat(3) { instrumentsList.add(Instrument("$it")) }

        repeat(100) {
            list.add(
                SolarFlare(
                    flrID = "$it-$it-$it",
                    instruments = instrumentsList,
                    beginTime = "beginTime",
                    peakTime = "peakTime",
                    endTime = "endTime",
                    classType = "classType",
                    sourceLocation = "sourceLocation",
                    activeRegionNum = Long.MAX_VALUE,
                    linkedEvents = linkedEventsList,
                    link = "link"
                )
            )
        }

        return list
    }
}