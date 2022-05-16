package com.project.astronomy.repository

import com.project.astronomy.entities.ItemRv

class MainRepository() {
    private val listItemRvAPOD: List<ItemRv> = listOf(
        ItemRv("Today", "rv_apod_today"),
    )

    private val listItemRvSolarFlare: List<ItemRv> = listOf(
        ItemRv("Solar Flare", "rv_solar_today"),
        ItemRv("Magnetic Storm", "rv_geo_today"),
    )

    private val listItemRvEpic: List<ItemRv> = listOf(
        ItemRv("Today", "rv_epic_today"),
        ItemRv("Before", "rv_epic_before"),
    )

    private val listItemRvMars: List<ItemRv> = listOf(
        ItemRv("Curiosity", "rv_mars_curiosity"),
        ItemRv("Spirit", "rv_mars_spirit"),
        ItemRv("Opportunity", "rv_mars_opportunity"),
    )

    fun getListRvAPOD() = listItemRvAPOD
    fun getListRvSolarFlare() = listItemRvSolarFlare
    fun getListRvEpic() = listItemRvEpic
    fun getListRvMars() = listItemRvMars
}