package com.project.astronomy.ui

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.project.astronomy.R
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainFragmentTest {

    private lateinit var scenario: FragmentScenario<MainFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer<MainFragment>(
            themeResId = com.google.android.material.R.style.Theme_MaterialComponents_NoActionBar)
    }

    @Test
    fun mainFragment_SwipeUp() {
        onView(withId(R.id.viewToScroll)).perform(swipeUp())
    }

    @Test
    fun mainFragment_SwipeDown() {
        onView(withId(R.id.viewToScroll)).perform(swipeDown())
    }

    @Test
    fun mainFragment_SolarSwipeLeft() {
        onView(withId(R.id.rv_solar)).perform(swipeLeft())
    }

    @Test
    fun mainFragment_SolarSwipeRight() {
        onView(withId(R.id.rv_solar)).perform(swipeRight())
    }

    @Test
    fun mainFragment_SwipeUpDown_Many() {
        for (i in 1..50) {
            onView(withId(R.id.viewToScroll)).perform(swipeUp())
            onView(withId(R.id.viewToScroll)).perform(swipeDown())
        }
    }

    @After
    fun close() {
        scenario.close()
    }
}