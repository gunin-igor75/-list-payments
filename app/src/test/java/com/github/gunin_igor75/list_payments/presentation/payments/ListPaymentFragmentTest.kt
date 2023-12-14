package com.github.gunin_igor75.list_payments.presentation.payments

import androidx.navigation.NavHostController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.github.gunin_igor75.list_payments.R
import com.github.gunin_igor75.list_payments.base.BaseRobolectricTest
import com.github.gunin_igor75.list_payments.di.DomainModule
import com.github.gunin_igor75.list_payments.domain.entity.PaymentsState
import com.github.gunin_igor75.list_payments.util.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast


@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@UninstallModules(DomainModule::class)
class ListPaymentFragmentTest : BaseRobolectricTest() {


    private lateinit var navHostController: NavHostController

    @Before
    override fun setup() {
        super.setup()
        setupNavHostController()
    }

    private fun setupNavHostController() {
        navHostController =
            spyk<NavHostController>(TestNavHostController(ApplicationProvider.getApplicationContext()))
        navHostController.setGraph(R.navigation.nav_graph)
    }

    @Test
    fun errorLoadingStateTest() {
        val state = MutableStateFlow(
            PaymentsState.ErrorLoading(
                error = "error"
            )
        )
        every {
            repository.loadPayments()
        } returns state

        launchFragmentInHiltContainer<ListPaymentFragment>(
            navHostController = navHostController
        )
        verify {
            navHostController.popBackStack(R.id.signFragment, false)
        }

        assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("Error authorization")
    }

    @Test
    fun errorBackandStateTest() {
        val state = MutableStateFlow(
            PaymentsState.ErrorBackend(
                error = "error"
            )
        )
        every {
            repository.loadPayments()
        } returns state

        launchFragmentInHiltContainer<ListPaymentFragment>(
            navHostController = navHostController
        )
        verify {
            navHostController.popBackStack(R.id.homeFragment, false)
        }
        assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("Cannot connect to the server")
    }
}
























