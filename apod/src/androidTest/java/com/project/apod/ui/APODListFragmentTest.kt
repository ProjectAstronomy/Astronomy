package com.project.apod.ui

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.project.apod.R
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class APODListFragmentTest {

    var scenario: FragmentScenario<APODListFragment>? = null

    @Before
    fun setup() {
        scenario = launchFragmentInContainer<APODListFragment>(
            themeResId = com.google.android.material.R.style.Theme_MaterialComponents_NoActionBar)
    }

    @Test
    fun scrollRecycler() {
        onView(withId(R.id.rv_list_apod_vertical)).perform(RecyclerViewActions.scrollToPosition<APODRecyclerViewAdapter.APODViewHolder>(
            5))
    }


    @After
    fun close() {
        scenario?.close()
    }

}