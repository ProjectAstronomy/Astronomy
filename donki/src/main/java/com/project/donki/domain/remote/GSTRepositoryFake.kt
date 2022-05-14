package com.project.donki.domain.remote

import com.project.core.domain.BaseRepository
import com.project.donki.entities.remote.AllKpIndex
import com.project.donki.entities.remote.GeomagneticStorm
import com.project.donki.entities.remote.LinkedEvent

class GSTRepositoryFake : BaseRepository<List<GeomagneticStorm>> {
    override suspend fun loadAsync(startDate: String, endDate: String): List<GeomagneticStorm> {
        val list = mutableListOf<GeomagneticStorm>()
        val allKpIndexList = mutableListOf<AllKpIndex>()
        val linkedEventsList = mutableListOf<LinkedEvent>()

        repeat(4) { linkedEventsList.add(LinkedEvent("$it")) }

        repeat(5) {
            allKpIndexList.add(
                AllKpIndex(observedTime = "observedTime", kpIndex = it.toLong(), source = "source")
            )
        }

        repeat(100) {
            list.add(
                GeomagneticStorm(
                    gstID = "$it-$it-$it",
                    startTime = "startTime",
                    allKpIndex = allKpIndexList,
                    linkedEvents = linkedEventsList,
                    link = "link"
                )
            )
        }

        return list
    }
}