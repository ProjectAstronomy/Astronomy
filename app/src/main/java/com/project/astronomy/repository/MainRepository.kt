package com.project.astronomy.repository

import com.project.astronomy.R
import com.project.astronomy.entities.ItemRv
import com.project.astronomy.utils.ResourceProvider
import com.project.astronomy.utils.*

class MainRepository(private val resourceProvider: ResourceProvider) {

    private val listItemRvSolarFlare: List<ItemRv> = listOf(
        ItemRv(resourceProvider.getString(R.string.title_main_solar), RV_SOLAR_TODAY),
        ItemRv(resourceProvider.getString(R.string.title_main_magnetic), RV_GEO_TODAY),
    )

    private val listItemRvEpic: List<ItemRv> = listOf(
        ItemRv(resourceProvider.getString(R.string.title_main_today), RV_EPIC_TODAY),
        ItemRv(resourceProvider.getString(R.string.title_main_before), RV_EPIC_BEFORE),
    )

    private val listItemRvMars: List<ItemRv> = listOf(
        ItemRv(resourceProvider.getString(R.string.title_main_curiosity), RV_MARS_CURIOSITY),
        ItemRv(resourceProvider.getString(R.string.title_main_spirit), RV_MARS_SPIRIT),
        ItemRv(resourceProvider.getString(R.string.title_main_opportunity), RV_MARS_OPPORTUNITY),
    )

    fun getListRvSolarFlare() = listItemRvSolarFlare
    fun getListRvEpic() = listItemRvEpic
    fun getListRvMars() = listItemRvMars
}