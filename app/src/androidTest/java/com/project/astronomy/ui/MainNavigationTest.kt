package com.project.astronomy.ui

import androidx.fragment.app.testing.FragmentScenario
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import com.google.android.material.R
import org.junit.Assert
import org.junit.Test

class MainNavigationTest {
    @Test
    fun onAPODClickListener() {
        val scenario = FragmentScenario.launchInContainer(
            MainFragment::class.java,
            themeResId = R.style.Theme_MaterialComponents_NoActionBar
        )
        scenario.onFragment { fragment ->
            Assert.assertNotNull(fragment.activity)
            val navController = TestNavHostController(fragment.activity!!)
            fragment.activity!!.runOnUiThread { navController.setGraph(com.project.astronomy.R.navigation.navigation_main) }
            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.navigate(com.project.astronomy.R.id.action_main_fragment_to_navigation_apod)
            val destination = navController.currentDestination
            Assert.assertNotNull(destination)
            Assert.assertEquals(destination!!.id, com.project.apod.R.id.fragment_apod)
        }
        scenario.close()
    }

    @Test
    fun onSolarFlareClickListener() {
        val scenario = FragmentScenario.launchInContainer(
            MainFragment::class.java,
            themeResId = R.style.Theme_MaterialComponents_NoActionBar
        )
        scenario.onFragment { fragment ->
            Assert.assertNotNull(fragment.activity)
            val navController = TestNavHostController(fragment.activity!!)
            fragment.activity!!.runOnUiThread { navController.setGraph(com.project.astronomy.R.navigation.navigation_main) }
            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.navigate(com.project.astronomy.R.id.action_main_fragment_to_navigation_flr)
            val destination = navController.currentDestination
            Assert.assertNotNull(destination)
            Assert.assertEquals(destination!!.id, com.project.donki.R.id.fragment_flr)
        }
        scenario.close()
    }

    @Test
    fun onGSTClickListener() {
        val scenario = FragmentScenario.launchInContainer(
            MainFragment::class.java,
            themeResId = R.style.Theme_MaterialComponents_NoActionBar
        )
        scenario.onFragment { fragment ->
            Assert.assertNotNull(fragment.activity)
            val navController = TestNavHostController(fragment.activity!!)
            fragment.activity!!.runOnUiThread { navController.setGraph(com.project.astronomy.R.navigation.navigation_main) }
            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.navigate(com.project.astronomy.R.id.action_main_fragment_to_navigation_gst)
            val destination = navController.currentDestination
            Assert.assertNotNull(destination)
            Assert.assertEquals(destination!!.id, com.project.donki.R.id.fragment_gst)
        }
        scenario.close()
    }

    @Test
    fun onEPICClickListener() {
        val scenario = FragmentScenario.launchInContainer(
            MainFragment::class.java,
            themeResId = R.style.Theme_MaterialComponents_NoActionBar
        )
        scenario.onFragment { fragment ->
            Assert.assertNotNull(fragment.activity)
            val navController = TestNavHostController(fragment.activity!!)
            fragment.activity!!.runOnUiThread { navController.setGraph(com.project.astronomy.R.navigation.navigation_main) }
            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.navigate(com.project.astronomy.R.id.action_main_fragment_to_navigation_epic)
            val destination = navController.currentDestination
            Assert.assertNotNull(destination)
            Assert.assertEquals(destination!!.id, com.project.epic.R.id.fragment_epic)
        }
        scenario.close()
    }

    @Test
    fun onMRPClickListener() {
        val scenario = FragmentScenario.launchInContainer(
            MainFragment::class.java,
            themeResId = R.style.Theme_MaterialComponents_NoActionBar
        )
        scenario.onFragment { fragment ->
            Assert.assertNotNull(fragment.activity)
            val navController = TestNavHostController(fragment.activity!!)
            fragment.activity!!.runOnUiThread { navController.setGraph(com.project.astronomy.R.navigation.navigation_main) }
            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.navigate(com.project.astronomy.R.id.action_main_fragment_to_navigation_mrp)
            val destination = navController.currentDestination
            Assert.assertNotNull(destination)
            Assert.assertEquals(destination!!.id, com.project.mrp.R.id.missionManifestFragment)
        }
        scenario.close()
    }
}