package com.github.gunin_igor75.list_payments.presentation.signin

import android.view.View
import androidx.navigation.NavController
import androidx.test.core.app.ActivityScenario
import com.github.gunin_igor75.list_payments.R
import com.github.gunin_igor75.list_payments.base.BaseRobolectricTest
import com.github.gunin_igor75.list_payments.di.DomainModule
import com.github.gunin_igor75.list_payments.domain.entity.SignInState
import com.github.gunin_igor75.list_payments.extension.with
import com.github.gunin_igor75.list_payments.util.launchNavHiltFragment
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.MutableStateFlow
import org.assertj.core.api.Assertions
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast

@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@UninstallModules(DomainModule::class)
class SignFragmentTest : BaseRobolectricTest() {

    @RelaxedMockK
    lateinit var navController: NavController

    private lateinit var scenario: ActivityScenario<*>

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun unauthorizedStateTest() {
        val state = MutableStateFlow(
            SignInState.Unauthorized(
                error = "error"
            )
        )
        every {
            repository.signIn(any(), any())
        } returns state

        scenario = launchNavHiltFragment<SignFragment>(navController)

        scenario.with {
            findViewById<View>(R.id.bt_sign_in).performClick()
        }

        Assertions.assertThat(ShadowToast.getTextOfLatestToast())
            .isEqualTo("Invalid email or password")
    }

    @Test
    fun errorBackandStateTest() {
        val state = MutableStateFlow(
            SignInState.ErrorBackend(
                error = "error"
            )
        )
        every {
            repository.signIn(any(), any())
        } returns state

        scenario = launchNavHiltFragment<SignFragment>(navController)

        scenario.with {
            findViewById<View>(R.id.bt_sign_in).performClick()
        }

        Assertions.assertThat(ShadowToast.getTextOfLatestToast())
            .isEqualTo("Cannot connect to the server")
    }
}