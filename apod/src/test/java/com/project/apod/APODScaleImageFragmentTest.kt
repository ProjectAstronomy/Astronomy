package com.project.apod

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import com.project.apod.ui.APODScaleImageFragment
import org.junit.After
import org.junit.Before

class APODScaleImageFragmentTest {
    private lateinit var scenario: FragmentScenario<APODScaleImageFragment>

    @Before
    fun setup() {
        scenario = launchFragment()
    }

    @After
    fun close() {
        scenario.close()
    }
}