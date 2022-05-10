package com.project.apod.ui

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.project.apod.R
import com.project.apod.entities.APODResponse
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class APODDescriptionFragmentTest {

    var scenario: FragmentScenario<APODDescriptionFragment>?= null

    @Before
    fun setup() {

        scenario = launchFragmentInContainer<APODDescriptionFragment>(
            themeResId = com.google.android.material.R.style.Theme_MaterialComponents_NoActionBar)
    }

    @Test
    fun tvTitleNotNull() {
        TestCase.assertNotNull(R.id.tv_title_apod)
    }

    @Test
    fun aPODDescriptionRecreate() {
        scenario?.recreate()
    }

    @Test
    fun testTextViewEmptyToStart() {
        onView(withId(R.id.tv_title_apod)).check(matches(withText("")))
    }

    @Test
    fun imageClick() {
        onView(withId(R.id.iv_url_apod)).perform(click())
    }

}