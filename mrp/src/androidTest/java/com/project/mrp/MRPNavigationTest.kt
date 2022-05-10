package com.project.mrp

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.project.core.di.androidNetworkStatusModule
import com.project.core.di.coreRepositoriesModule
import com.project.core.di.retrofitModule
import com.project.mrp.di.missionManifestModule
import com.project.mrp.di.photosModule
import com.project.mrp.entities.PhotosInformation
import com.project.mrp.ui.MissionManifestFragment
import com.project.mrp.ui.MissionManifestFragmentDirections
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin

@RunWith(AndroidJUnit4::class)
class MRPNavigationTest {
    @Before
    fun setup() {
        startKoin {
            modules(
                listOf(
                    retrofitModule,
                    coreRepositoriesModule,
                    androidNetworkStatusModule,
                    missionManifestModule,
                    photosModule
                )
            )
        }
    }

    @Test
    fun testNavigationFromMissionManifestFragmentToPhotosFragment() {
        val roverNames = listOf("Spirit", "Opportunity", "Curiosity")
        val photosInformation = PhotosInformation(
            sol = 0,
            earthDate = "2012-08-06",
            totalPhotos = 3702,
            cameras = listOf("CHEMCAM", "FHAZ", "MARDI", "RHAZ")
        )
        val scenario = FragmentScenario.launchInContainer(
            MissionManifestFragment::class.java,
            themeResId = com.google.android.material.R.style.Theme_MaterialComponents_NoActionBar,
            fragmentArgs = bundleOf("roverName" to roverNames[0])
        )
        scenario.onFragment { fragment ->
            TestCase.assertNotNull(fragment.activity)
            val navController = TestNavHostController(fragment.activity!!)
            fragment.activity!!.runOnUiThread {
                navController.setGraph(R.navigation.navigation_mrp)
            }
            Navigation.setViewNavController(fragment.requireView(), navController)
            val action = MissionManifestFragmentDirections
                .actionMissionManifestFragmentToPhotosFragment(roverNames[0], photosInformation)
            navController.navigate(action)
            val destination = navController.currentDestination
            TestCase.assertNotNull(destination)
            TestCase.assertEquals(destination!!.id, R.id.photosFragment)
            val arguments = navController.backStack.last().arguments
            TestCase.assertNotNull(arguments)
            TestCase.assertEquals(roverNames[0], arguments!!.get("roverName"))
            TestCase.assertEquals(photosInformation, arguments.get("photosInformation"))
        }
        scenario.close()
    }
}