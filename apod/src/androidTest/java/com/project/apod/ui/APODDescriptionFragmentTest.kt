package com.project.apod.ui

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.FragmentScenario.Companion.launchInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.project.apod.R
import com.project.apod.entities.remote.APODResponse
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class APODDescriptionFragmentTest {
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

    private var scenario: FragmentScenario<APODDescriptionFragment>? = null

    @Before
    fun setup() {
        scenario = launchInContainer(
            APODDescriptionFragment::class.java,
            themeResId = com.google.android.material.R.style.Theme_MaterialComponents_NoActionBar,
            fragmentArgs = bundleOf("apodResponse" to apodResponse)
        )
    }

    @Test
    fun testViewsNotNull() {
        scenario?.moveToState(Lifecycle.State.RESUMED)
        scenario?.onFragment { fragment ->
            assertEquals(apodResponse, fragment.navArgs.apodResponse)
        }
        onView(withId(R.id.iv_url_apod)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.tv_title_apod)).check(matches(withText(apodResponse.title)))
        onView(withId(R.id.tv_date_apod)).check(matches(withText(apodResponse.date)))
        onView(withId(R.id.tv_explanation_apod)).check(matches(withText(apodResponse.explanation)))
        onView(withId(R.id.tv_copyright_apod)).check(matches(withText(apodResponse.copyright)))
    }

    @After
    fun close() {
        scenario?.close()
    }
}