package com.project.apod.ui

import android.view.View
import androidx.fragment.app.testing.FragmentScenario
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.project.apod.R
import com.project.apod.di.apodModule
import com.project.apod.ui.APODRecyclerViewAdapter.APODViewHolder
import com.project.core.di.androidNetworkStatusModule
import com.project.core.di.coreRepositoriesModule
import com.project.core.di.retrofitModule
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin

@RunWith(AndroidJUnit4::class)
class APODListFragmentTest {
    companion object {
        private const val FAKE = "FAKE"
        private const val REAL = "REAL"
    }

    private var scenario: FragmentScenario<APODListFragment>? = null

    @Before
    fun setup() {
        startKoin {
            modules(listOf(retrofitModule, coreRepositoriesModule, androidNetworkStatusModule, apodModule))
        }
        scenario = FragmentScenario.launchInContainer(
            APODListFragment::class.java,
            themeResId = com.google.android.material.R.style.Theme_MaterialComponents_NoActionBar
        )
    }

    @Test
    fun testScrolling() {
        onView(withId(R.id.rv_list_apod_vertical))
            .perform(RecyclerViewActions.scrollTo<APODViewHolder>(
                hasDescendant(withText("2022-04-05")))
            )
    }

    @Test
    fun performClickAtPosition() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        scenario?.onFragment { fragment ->
            navController.setGraph(R.navigation.navigation_apod)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        onView(withId(R.id.rv_list_apod_vertical))
            .perform(RecyclerViewActions.actionOnItemAtPosition<APODViewHolder>(3, click()))
        assertEquals(navController.currentDestination?.id, R.id.fragment_apod_description)
    }

    @Test
    fun performClickOnItem() {
        val date = "2022-04-05"
        onView(withId(R.id.rv_list_apod_vertical))
            .perform(RecyclerViewActions.scrollTo<APODViewHolder>(
                hasDescendant(withText(date)))
            )
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        scenario?.onFragment { fragment ->
            navController.setGraph(R.navigation.navigation_apod)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        onView(withId(R.id.rv_list_apod_vertical))
            .perform(RecyclerViewActions.actionOnItem<APODViewHolder>(hasDescendant(withText(date)), click()))
        assertEquals(navController.currentDestination?.id, R.id.fragment_apod_description)
    }

    @After
    fun close() {
        scenario?.close()
    }

    private fun delay(): ViewAction = object : ViewAction {
        override fun getConstraints(): Matcher<View> = isRoot()

        override fun getDescription(): String = "Wait for 2 seconds"

        override fun perform(uiController: UiController?, view: View?) {
            uiController?.loopMainThreadForAtLeast(2000)
        }
    }
}