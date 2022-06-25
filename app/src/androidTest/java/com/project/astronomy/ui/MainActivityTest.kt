package com.project.astronomy.ui

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test fun activity_AssertNotNUll() {
        scenario.onActivity {
            TestCase.assertNotNull(it)
        }
    }

    @Test fun activity_IsResumed() {
        TestCase.assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @After
    fun close() {
        scenario.close()
    }
}