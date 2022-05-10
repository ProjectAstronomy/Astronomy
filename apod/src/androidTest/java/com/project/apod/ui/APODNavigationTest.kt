package com.project.apod.ui

import androidx.fragment.app.testing.FragmentScenario
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import com.project.apod.R
import com.project.apod.entities.APODResponse
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Test

class APODNavigationTest {
    private val apodResponse = APODResponse(
        copyright = "copyright",
        date = "date",
        explanation = "explanation",
        hdurl = "hdurl",
        mediaType = "mediaType",
        serviceVersion = "serviceVersion",
        title = "title",
        url = "url"
    )

    @Test
    fun testNavigationFromAPODListFragmentToAPODDescriptionFragment() {
        val scenario = FragmentScenario.launchInContainer(
            APODListFragment::class.java,
            themeResId = com.google.android.material.R.style.Theme_MaterialComponents_NoActionBar
        )
        scenario.onFragment { fragment ->
            assertNotNull(fragment.activity)
            val navController = TestNavHostController(fragment.activity!!)
            fragment.activity!!.runOnUiThread {
                navController.setGraph(R.navigation.navigation_apod)
            }
            Navigation.setViewNavController(fragment.requireView(), navController)
            val action = APODListFragmentDirections
                .actionFragmentApodToFragmentApodDescription(apodResponse)
            navController.navigate(action)
            val destination = navController.currentDestination
            assertNotNull(destination)
            assertEquals(destination!!.id, R.id.fragment_apod_description)
            val arguments = navController.backStack.last().arguments
            assertNotNull(arguments)
            assertEquals(apodResponse, arguments!!.get("apodResponse"))
        }
        scenario.close()
    }

    @Test
    fun testNavigationFromAPODDescriptionFragmentToAPODScaleImageFragment() {
        val scenario = FragmentScenario.launchInContainer(
            APODDescriptionFragment::class.java,
            themeResId = com.google.android.material.R.style.Theme_MaterialComponents_NoActionBar
        )
        scenario.onFragment { fragment ->
            assertNotNull(fragment.activity)
            val navController = TestNavHostController(fragment.activity!!)
            fragment.activity!!.runOnUiThread {
                navController.setGraph(R.navigation.navigation_apod)
            }
            Navigation.setViewNavController(fragment.requireView(), navController)
            val action = APODDescriptionFragmentDirections
                .actionFragmentApodDescriptionToAPODScaleImageFragment(apodResponse)
            navController.navigate(action)
            val destination = navController.currentDestination
            assertNotNull(destination)
            assertEquals(destination!!.id, R.id.APODScaleImageFragment)
            val arguments = navController.backStack.last().arguments
            assertNotNull(arguments)
            assertEquals(apodResponse, arguments!!.get("apodResponse"))
        }
        scenario.close()
    }
}