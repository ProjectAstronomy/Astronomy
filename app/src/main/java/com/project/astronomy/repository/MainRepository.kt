package com.project.astronomy.repository

import com.project.astronomy.R
import com.project.astronomy.entities.ItemRv
import com.project.astronomy.ui.ResourceProvider

class MainRepository(private val resourceProvider: ResourceProvider) {

    companion object {
        const val RV_APOD_TODAY = "rv_apod_today"
        const val RV_SOLAR_TODAY = "rv_solar_today"
        const val RV_GEO_TODAY = "rv_geo_today"
        const val RV_EPIC_TODAY = "rv_epic_today"
        const val RV_EPIC_BEFORE = "rv_epic_before"
        const val RV_MARS_CURIOSITY = "rv_mars_curiosity"
        const val RV_MARS_SPIRIT = "rv_mars_spirit"
        const val RV_MARS_OPPORTUNITY = "rv_mars_opportunity"
    }

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