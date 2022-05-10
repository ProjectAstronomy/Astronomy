package com.project.apod.ui

import androidx.fragment.app.testing.FragmentScenario.Companion.launchInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import com.project.apod.R
import com.project.apod.di.apodModule
import com.project.apod.entities.APODResponse
import com.project.core.di.androidNetworkStatusModule
import com.project.core.di.coreRepositoriesModule
import com.project.core.di.retrofitModule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin

class APODNavigationTest {
    private val apodResponse = APODResponse(
        copyright = "Robert Eder",
        date = "2022-02-17",
        explanation = "Dark markings and bright nebulae in this telescopic southern sky view are telltale signs of young stars and active star formation. They lie a mere 650 light-years away, at the boundary of the local bubble and the Chamaeleon molecular cloud complex. Regions with young stars identified as dusty reflection nebulae from the 1946 Cederblad catalog include the C-shaped Ced 110 just above and left of center, and bluish Ced 111 below it. Also a standout in the frame, the orange tinted V-shape of the Chamaeleon Infrared Nebula (Cha IRN) was carved by material streaming from a newly formed low-mass star.  The well-composed image spans 1.5 degrees. That's about 17 light-years at the estimated distance of the nearby Chamaeleon I molecular cloud.",
        hdurl = "https://apod.nasa.gov/apod/image/2202/Chamaeleon_RobertEder.jpg",
        mediaType = "image",
        serviceVersion = "v1",
        title = "Chamaeleon I Molecular Cloud",
        url = "https://apod.nasa.gov/apod/image/2202/Chamaeleon_RobertEder1024.jpg"
    )

    @Before
    fun setup() {
        startKoin { modules(listOf(retrofitModule, coreRepositoriesModule, androidNetworkStatusModule, apodModule)) }
    }

    @Test
    fun testNavigationFromAPODListFragmentToAPODDescriptionFragment() {
        val scenario = launchInContainer(
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
}