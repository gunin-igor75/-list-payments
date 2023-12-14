package com.github.gunin_igor75.list_payments.presentation.payments

import androidx.navigation.NavHostController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.github.gunin_igor75.list_payments.R
import com.github.gunin_igor75.list_payments.di.DomainModule
import com.github.gunin_igor75.list_payments.domain.entity.Payment
import com.github.gunin_igor75.list_payments.domain.entity.PaymentsState
import com.github.gunin_igor75.list_payments.utils.BaseTest
import com.github.gunin_igor75.list_payments.utils.epresso.atPosition
import com.github.gunin_igor75.list_payments.utils.epresso.scrollToPosition
import com.github.gunin_igor75.list_payments.utils.epresso.withItemsCount
import com.github.gunin_igor75.list_payments.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@UninstallModules(DomainModule::class)
@MediumTest
class ListPaymentFragmentTest : BaseTest() {

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
    fun initializationStateTest() {
        val state = MutableStateFlow(PaymentsState.Initialization)
        every {
            repository.loadPayments()
        } returns state
        launchFragmentInHiltContainer<ListPaymentFragment>(
            navHostController = navHostController
        )

        onView(withId(R.id.pb_loading))
            .check(matches(not(isDisplayed())))

    }

    @Test
    fun loadingStateTest() {
        val state = MutableStateFlow(PaymentsState.Loading)
        every {
            repository.loadPayments()
        } returns state
        launchFragmentInHiltContainer<ListPaymentFragment>(
            navHostController = navHostController)
        onView(withId(R.id.pb_loading))
            .check(matches(isDisplayed()))
    }

    @Test
    fun contentStateTest() {
        val payments = listOf(
            Payment(
                id = 1,
                title = "Test Payment 1",
                amount = BigDecimal("101.00"),
                created = "12 мая , пт"
            ),
            Payment(
                id = 2,
                title = "Test Payment 2",
                amount = BigDecimal("111.11"),
                created = "17 мая , ср"
            )
        )
        val state = MutableStateFlow(
            PaymentsState.Content(
                payments = payments
            )
        )
        every {
            repository.loadPayments()
        } returns state
        launchFragmentInHiltContainer<ListPaymentFragment>(
            navHostController = navHostController)
        onView(withId(R.id.rv_payment))
            .perform(scrollToPosition(0))
            .check(
                matches(
                    atPosition(
                        0, allOf(
                            hasDescendant(allOf(withId(R.id.tv_created), withText("12 мая , пт"))),
                            hasDescendant(
                                allOf(
                                    withId(R.id.tv_title_payment),
                                    withText("Test Payment 1")
                                )
                            ),
                            hasDescendant(allOf(withId(R.id.tv_amount_payment), withText("101.00")))
                        )
                    )
                )
            )
        onView(withId(R.id.rv_payment))
            .perform(scrollToPosition(1))
            .check(
                matches(
                    atPosition(
                        1, allOf(
                            hasDescendant(allOf(withId(R.id.tv_created), withText("17 мая , ср"))),
                            hasDescendant(
                                allOf(
                                    withId(R.id.tv_title_payment),
                                    withText("Test Payment 2")
                                )
                            ),
                            hasDescendant(allOf(withId(R.id.tv_amount_payment), withText("111.11")))
                        )
                    )
                )
            )

        onView(withId(R.id.rv_payment))
            .check(matches(withItemsCount(2)))
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
            navHostController = navHostController)
        verify {
            navHostController.popBackStack(R.id.signFragment, false)
        }
    }

    @Test
    fun errorBackendStateTest() {
        val state = MutableStateFlow(
            PaymentsState.ErrorBackend(
                error = "error"
            )
        )
        every {
            repository.loadPayments()
        } returns state

        launchFragmentInHiltContainer<ListPaymentFragment>(
            navHostController = navHostController)
        verify {
            navHostController.popBackStack(R.id.homeFragment, false)
        }
    }
}