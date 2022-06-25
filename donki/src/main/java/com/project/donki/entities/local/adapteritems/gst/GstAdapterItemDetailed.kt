package com.project.donki.entities.local.adapteritems.gst

data class GstAdapterItemDetailed(
     val startTime: String?,
     val observedTime: String?,
     val kpIndex: Long?,
     val source: String?
) : IGstAdapterItem