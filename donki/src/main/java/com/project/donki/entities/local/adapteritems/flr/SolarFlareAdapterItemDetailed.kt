package com.project.donki.entities.local.adapteritems.flr

data class SolarFlareAdapterItemDetailed (
        val flrID: String,
        val beginTime: String?,
        val peakTime: String?,
        val endTime: String?,
        val classType: String?,
        val sourceLocation: String?,
        val activeRegionNum: Long?,
        val link: String?
        ) : ISolarFlareAdapterItem
