package com.github.gunin_igor75.list_payments.presentation.home

import androidx.navigation.NavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.github.gunin_igor75.list_payments.R
import com.github.gunin_igor75.list_payments.di.DomainModule
import com.github.gunin_igor75.list_payments.domain.settings.TokenSettings
import com.github.gunin_igor75.list_payments.presentation.home.*
import com.github.gunin_igor75.list_payments.utils.BaseTest
import com.github.gunin_igor75.list_payments.utils.launchNavHiltFragment
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@UninstallModules(DomainModule::class)
@MediumTest
class HomeFragmentTest : BaseTest() {

    @RelaxedMockK
    lateinit var navController: NavController

    lateinit var scenario: AutoCloseable

    @Inject
    lateinit var tokenSettings: TokenSettings

    @Before
    override fun setup() {
        super.setup()
        coEvery {
            repository.logOut()
        } just runs

        scenario = launchNavHiltFragment<HomeFragment>(navController)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun clickShowPaymentsTokenIsNulTest() {
        tokenSettings.setCurrentToken(null)
        onView(withId(R.id.bt_payments))
            .perform(click())

        verify {
            navController.navigate(R.id.action_homeFragment_to_signFragment)
        }
    }

    @Test
    fun clickShowPaymentsTokenIsNotNulTest() {
        tokenSettings.setCurrentToken("token")
        onView(withId(R.id.bt_payments))
            .perform(click())

        verify {
            navController.navigate(R.id.action_homeFragment_to_listPaymentFragment)
        }
    }
    @Test
    fun clickLogoutTest() {
        onView(withId(R.id.bt_log_out))
            .perform(click())

        coVerifyOrder {
            navController.navigate(R.id.action_homeFragment_to_signFragment)
            repository.logOut()
        }
    }
}