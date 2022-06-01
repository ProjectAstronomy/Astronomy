package com.project.astronomy.repository

import com.project.astronomy.entities.ItemRv
import com.project.astronomy.utils.ResourceProvider
import com.project.astronomy.utils.*

class MainRepository(private val resourceProvider: ResourceProvider) {

    private val listItemRvAPOD: List<ItemRv> = listOf(
        ItemRv("Today", RV_APOD_TODAY),
    )

    private val listItemRvSolarFlare: List<ItemRv> = listOf(
        ItemRv("Solar Flare", RV_SOLAR_TODAY),
        ItemRv("Magnetic Storm", RV_GEO_TODAY),
    )

    private val listItemRvEpic: List<ItemRv> = listOf(
        ItemRv("Today", RV_EPIC_TODAY),
        ItemRv("Before", RV_EPIC_BEFORE),
    )

    private val listItemRvMars: List<ItemRv> = listOf(
        ItemRv("Curiosity", RV_MARS_CURIOSITY),
        ItemRv("Spirit", RV_MARS_SPIRIT),
        ItemRv("Opportunity", RV_MARS_OPPORTUNITY),
    )

    fun getListRvAPOD() = listItemRvAPOD
    fun getListRvSolarFlare() = listItemRvSolarFlare
    fun getListRvEpic() = listItemRvEpic
    fun getListRvMars() = listItemRvMars
}