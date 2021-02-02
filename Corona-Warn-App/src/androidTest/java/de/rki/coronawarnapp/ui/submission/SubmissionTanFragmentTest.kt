package de.rki.coronawarnapp.ui.submission

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.rki.coronawarnapp.R
import de.rki.coronawarnapp.submission.SubmissionRepository
import de.rki.coronawarnapp.ui.submission.tan.SubmissionTanFragment
import de.rki.coronawarnapp.ui.submission.tan.SubmissionTanViewModel
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import testhelpers.BaseUITest
import testhelpers.SCREENSHOT_DELAY_TIME
import testhelpers.Screenshot
import testhelpers.SystemUIDemoModeRule
import testhelpers.TestDispatcherProvider
import testhelpers.launchFragment2
import testhelpers.launchFragmentInContainer2
import tools.fastlane.screengrab.Screengrab
import tools.fastlane.screengrab.locale.LocaleTestRule

@RunWith(AndroidJUnit4::class)
class SubmissionTanFragmentTest : BaseUITest() {

    @MockK lateinit var submissionRepository: SubmissionRepository

    private fun createViewModel() = SubmissionTanViewModel(
        dispatcherProvider = TestDispatcherProvider(),
        submissionRepository = submissionRepository
    )

    @Rule
    @JvmField
    val localeTestRule = LocaleTestRule()

    @get:Rule
    val systemUIDemoModeRule = SystemUIDemoModeRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)

        setupMockViewModel(
            object : SubmissionTanViewModel.Factory {
                override fun create(): SubmissionTanViewModel = createViewModel()
            }
        )
    }

    @After
    fun teardown() {
        clearAllViewModels()
        unmockkAll()
    }

    @Test
    fun launch_fragment() {
        launchFragment2<SubmissionTanFragment>()
    }

    @Test
    fun testEventTanNextClicked() {
        launchFragmentInContainer2<SubmissionTanFragment>()
        closeSoftKeyboard()
        onView(withId(R.id.submission_tan_button_enter))
            .perform(click())
    }

    @Test
    @Screenshot
    fun capture_fragment_empty() {
        launchFragmentInContainer2<SubmissionTanFragment>()
        onView(withId(R.id.tan_input_edittext))
            .perform(click())
            .perform(closeSoftKeyboard())
        Thread.sleep(SCREENSHOT_DELAY_TIME)
        Screengrab.screenshot(SubmissionTanFragment::class.simpleName)
    }

    @Test
    @Screenshot
    fun capture_fragment_done() {
        launchFragmentInContainer2<SubmissionTanFragment>()
        onView(withId(R.id.tan_input_edittext))
            .perform(click())
            .perform(typeText("AC9UHD65AF"), closeSoftKeyboard())
        Thread.sleep(SCREENSHOT_DELAY_TIME)
        Screengrab.screenshot(SubmissionTanFragment::class.simpleName.plus("_done"))
    }

    @Test
    @Screenshot
    fun capture_fragment_invalid() {
        launchFragmentInContainer2<SubmissionTanFragment>()
        onView(withId(R.id.tan_input_edittext))
            .perform(click())
            .perform(typeText("AC9U0"), closeSoftKeyboard())
        Thread.sleep(SCREENSHOT_DELAY_TIME)
        Screengrab.screenshot(SubmissionTanFragment::class.simpleName.plus("_invalid"))
    }
}

@Module
abstract class SubmissionTanTestModule {
    @ContributesAndroidInjector
    abstract fun submissionTanScreen(): SubmissionTanFragment
}
