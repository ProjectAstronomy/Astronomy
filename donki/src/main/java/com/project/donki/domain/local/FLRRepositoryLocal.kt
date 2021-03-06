package com.project.donki.domain.local

import com.project.donki.domain.local.dao.InstrumentDao
import com.project.donki.domain.local.dao.LinkedEventDao
import com.project.donki.domain.local.dao.SolarFlareDao
import com.project.donki.entities.local.InstrumentEntity
import com.project.donki.entities.local.LinkedEventEntity
import com.project.donki.entities.local.SolarFlareEntity
import com.project.donki.entities.remote.Instrument
import com.project.donki.entities.remote.LinkedEvent
import com.project.donki.entities.remote.SolarFlare

class FLRRepositoryLocal(
    private val solarFlareDao: SolarFlareDao,
    private val instrumentDao: InstrumentDao,
    private val linkedEventDao: LinkedEventDao
) {
    suspend fun insertSolarFlare(solarFlare: SolarFlare) {
        solarFlareDao.insertSolarFlare(convert(solarFlare))
        solarFlare.instruments?.let { insertInstruments(it, solarFlare.flrID) }
        solarFlare.linkedEvents?.let { insertLinkedEvents(it, solarFlare.flrID) }
    }

    suspend fun getAll(): List<SolarFlare> {
        val solarFlares = solarFlareDao.getAll()
        for (solarFlare in solarFlares) {
            solarFlare.instruments = instrumentDao.getInstrumentsBySolarFlareId(solarFlare.flrID)
            solarFlare.linkedEvents = linkedEventDao.getLinkedEventBySolarFlareId(solarFlare.flrID)
        }
        return solarFlares.map { convert(it) }
    }

    private suspend fun insertInstruments(instruments: List<Instrument>, flrID: String) {
        for (instrument in instruments) {
            instrumentDao.insertInstruments(
                InstrumentEntity(displayName = instrument.displayName, flrID = flrID)
            )
        }
    }

    private suspend fun insertLinkedEvents(linkedEvents: List<LinkedEvent>, flrID: String) {
        for (linkedEvent in linkedEvents) {
            linkedEventDao.insertLinkedEvents(
                LinkedEventEntity(activityID = linkedEvent.activityID, flrID = flrID)
            )
        }
    }

    private fun convert(solarFlare: SolarFlare): SolarFlareEntity =
        SolarFlareEntity(
            flrID = solarFlare.flrID,
            beginTime = solarFlare.beginTime,
            peakTime = solarFlare.peakTime,
            endTime = solarFlare.endTime,
            classType = solarFlare.classType,
            sourceLocation = solarFlare.sourceLocation,
            activeRegionNum = solarFlare.activeRegionNum,
            link = solarFlare.link
        )

    private fun convert(solarFlareEntity: SolarFlareEntity): SolarFlare {
        return SolarFlare(
            flrID = solarFlareEntity.flrID,
            instruments = solarFlareEntity.instruments?.map { Instrument(it.displayName) },
            beginTime = solarFlareEntity.beginTime,
            peakTime = solarFlareEntity.peakTime,
            endTime = solarFlareEntity.endTime,
            classType = solarFlareEntity.classType,
            sourceLocation = solarFlareEntity.sourceLocation,
            activeRegionNum = solarFlareEntity.activeRegionNum,
            linkedEvents = solarFlareEntity.linkedEvents?.map { LinkedEvent(it.activityID) },
            link = solarFlareEntity.link
        )
    }
}