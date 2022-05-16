package com.project.donki.domain.local

import com.project.donki.entities.local.AllKpIndexEntity
import com.project.donki.entities.local.GeomagneticStormEntity
import com.project.donki.entities.local.LinkedEventEntity
import com.project.donki.entities.remote.AllKpIndex
import com.project.donki.entities.remote.GeomagneticStorm
import com.project.donki.entities.remote.LinkedEvent

class GSTRepositoryLocal(
    private val geomagneticStormDao: GeomagneticStormDao,
    private val linkedEventDao: LinkedEventDao,
    private val allKpIndexDao: AllKpIndexDao
) {
    suspend fun insertGeomagneticStorm(geomagneticStorm: GeomagneticStorm) {
        geomagneticStormDao.insertGeomagneticStorm(convert(geomagneticStorm))
        geomagneticStorm.allKpIndex?.let { insertAllKpIndices(it, geomagneticStorm.gstID) }
        geomagneticStorm.linkedEvents?.let {
            insertLinkedEvents(it, gstID = geomagneticStorm.gstID)
        }
    }

    suspend fun getAll(): List<GeomagneticStorm> {
        val geomagneticStorms = geomagneticStormDao.getAll()
        for (geomagneticStorm in geomagneticStorms) {
            geomagneticStorm.allKpIndex =
                allKpIndexDao.getAllKpIndexByGeomagneticStormId(geomagneticStorm.gstID)
            geomagneticStorm.linkedEvents =
                linkedEventDao.getLinkedEventBySolarFlareId(geomagneticStorm.gstID)
        }
        return geomagneticStorms.map { convert(it) }
    }

    private suspend fun insertAllKpIndices(allKpIndices: List<AllKpIndex>, gstID: String) {
        for (allKpIndex in allKpIndices) {
            allKpIndexDao.insertAllKpIndex(
                AllKpIndexEntity(
                    observedTime = allKpIndex.observedTime,
                    kpIndex = allKpIndex.kpIndex,
                    source = allKpIndex.source,
                    gstID = gstID
                )
            )
        }
    }

    private suspend fun insertLinkedEvents(linkedEvents: List<LinkedEvent>, gstID: String) {
        for (linkedEvent in linkedEvents) {
            linkedEventDao.insertLinkedEvents(
                LinkedEventEntity(activityID = linkedEvent.activityID, gstID = gstID)
            )
        }
    }

    private fun convert(geomagneticStorm: GeomagneticStorm): GeomagneticStormEntity =
        GeomagneticStormEntity(
            gstID = geomagneticStorm.gstID,
            startTime = geomagneticStorm.startTime,
            link = geomagneticStorm.link
        )

    private fun convert(geomagneticStormEntity: GeomagneticStormEntity): GeomagneticStorm =
        GeomagneticStorm(
            gstID = geomagneticStormEntity.gstID,
            startTime = geomagneticStormEntity.startTime,
            link = geomagneticStormEntity.link,
            allKpIndex = geomagneticStormEntity.allKpIndex?.map {
                AllKpIndex(
                    observedTime = it.observedTime,
                    source = it.source,
                    kpIndex = it.kpIndex
                )
            },
            linkedEvents = geomagneticStormEntity.linkedEvents
                ?.map { LinkedEvent(activityID = it.activityID) }
        )
}