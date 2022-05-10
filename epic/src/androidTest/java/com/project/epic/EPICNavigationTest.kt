package com.project.epic

import androidx.fragment.app.testing.FragmentScenario.Companion.launchInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.project.core.di.androidNetworkStatusModule
import com.project.core.di.coreRepositoriesModule
import com.project.core.di.retrofitModule
import com.project.epic.di.epicModule
import com.project.epic.entities.AttitudeQuaternions
import com.project.epic.entities.CentroidCoordinates
import com.project.epic.entities.EPICResponse
import com.project.epic.entities.J2000Position
import com.project.epic.ui.EPICListFragment
import com.project.epic.ui.EPICListFragmentDirections
import junit.framework.TestCase

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before
import org.koin.core.context.startKoin

@RunWith(AndroidJUnit4::class)
class EPICNavigationTest {
    private val epicResponse = EPICResponse(
        identifier = "20220509005516",
        caption = "This image was taken by NASA's EPIC camera onboard the NOAA DSCOVR spacecraft",
        image = "epic_1b_20220509005516",
        version = "03",
        centroidCoordinates = CentroidCoordinates(lat = 23.422852, lon = 167.34375),
        dscovrJ2000Position = J2000Position(x = 983988.385152, y = 1077807.482015, z = 630462.446625),
        lunarJ2000Position = J2000Position(x = -296279.235866, y = 227189.683637, z = 138400.573564),
        sunJ2000Position = J2000Position(x = 100838181.746278, y = 103123837.298554, z = 44703383.950255),
        attitudeQuaternions = AttitudeQuaternions(q0 = 0.241879, q1 = -0.052192, q2 = -0.898198, q3 = 0.363334),
        date = "2022-05-09 00:50:27"
    )

    @Before
    fun setup() {
        startKoin { modules(listOf(retrofitModule, coreRepositoriesModule, androidNetworkStatusModule, epicModule)) }
    }

    @Test
    fun testNavigationFromEPICListFragmentToEPICDescriptionFragment() {
        val scenario = launchInContainer(
            EPICListFragment::class.java,
            themeResId = com.google.android.material.R.style.Theme_MaterialComponents_NoActionBar
        )
        scenario.onFragment { fragment ->
            TestCase.assertNotNull(fragment.activity)
            val navController = TestNavHostController(fragment.activity!!)
            fragment.activity!!.runOnUiThread {
                navController.setGraph(R.navigation.navigation_epic)
            }
            Navigation.setViewNavController(fragment.requireView(), navController)
            val action = EPICListFragmentDirections
                .actionFragmentEpicToFragmentEpicDescription(epicResponse)
            navController.navigate(action)
            val destination = navController.currentDestination
            TestCase.assertNotNull(destination)
            TestCase.assertEquals(destination!!.id, R.id.fragment_epic_description)
            val arguments = navController.backStack.last().arguments
            TestCase.assertNotNull(arguments)
            TestCase.assertEquals(epicResponse, arguments!!.get("epicResponse"))
        }
        scenario.close()
    }
}