package com.github.gunin_igor75.list_payments.presentation.signin

import androidx.navigation.NavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.github.gunin_igor75.list_payments.R
import com.github.gunin_igor75.list_payments.di.DomainModule
import com.github.gunin_igor75.list_payments.domain.entity.SignInState
import com.github.gunin_igor75.list_payments.utils.BaseTest
import com.github.gunin_igor75.list_payments.utils.epresso.hasTextILError
import com.github.gunin_igor75.list_payments.utils.epresso.hasTextILHint
import com.github.gunin_igor75.list_payments.utils.launchNavHiltFragment
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@UninstallModules(DomainModule::class)
@MediumTest
class SignFragmentTest : BaseTest() {

    @RelaxedMockK
    lateinit var navController: NavController

    private lateinit var scenario: AutoCloseable


    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun initializationTest() {
        val state = MutableStateFlow(SignInState.Initialization)
        every {
            repository.signIn(any(), any())
        } returns state
        scenario = launchNavHiltFragment<SignFragment>(navController)

        onView(withId(R.id.ti_password))
            .check(matches(hasTextILHint(PASSWORD)))

        onView(withId(R.id.ti_login))
            .check(matches(hasTextILHint(LOGIN)))

        onView(withId(R.id.pb_sign_in))
            .check(matches( not(isDisplayed())))
    }

    @Test
    fun loadingTest() {
        val state = MutableStateFlow(SignInState.Loading)
        every {
            repository.signIn(any(), any())
        } returns state
        scenario = launchNavHiltFragment<SignFragment>(navController)

        onView(withId(R.id.bt_sign_in))
            .perform(click())

        onView(withId(R.id.pb_sign_in))
            .check(matches( isDisplayed()))
    }

    @Test
    fun authorizationTest() {
        val state = MutableStateFlow(SignInState.Authorization)
        every {
            repository.signIn(any(), any())
        } returns state
        scenario = launchNavHiltFragment<SignFragment>(navController)

        onView(withId(R.id.bt_sign_in))
            .perform(click())

        verify {
            navController.navigate(R.id.action_signFragment_to_listPaymentFragment)
        }
    }

    @Test
    fun emptyPasswordTest() {
        val state = MutableStateFlow(SignInState.EmptyField(
            emptyPassword = true
        ))
        every {
            repository.signIn(any(), any())
        } returns state
        scenario = launchNavHiltFragment<SignFragment>(navController)

        onView(withId(R.id.bt_sign_in))
            .perform(click())

        onView(withId(R.id.ti_password))
            .check(matches(hasTextILError(FIELD_EMPTY)))
    }

    @Test
    fun emptyLoginTest() {
        val state = MutableStateFlow(SignInState.EmptyField(
            emptyLogin = true
        ))
        every {
            repository.signIn(any(), any())
        } returns state
        scenario = launchNavHiltFragment<SignFragment>(navController)

        onView(withId(R.id.bt_sign_in))
            .perform(click())

        onView(withId(R.id.ti_login))
            .check(matches(hasTextILError(FIELD_EMPTY)))
    }

    @Test
    fun clearFieldTest() {
        val state = MutableStateFlow(SignInState.ClearField)
        every {
            repository.signIn(any(), any())
        } returns state
        scenario = launchNavHiltFragment<SignFragment>(navController)



        onView(withId(R.id.bt_sign_in))
            .perform(click())

        onView(withId(R.id.ti_password))
            .check(matches(hasTextILHint(PASSWORD)))

        onView(withId(R.id.ti_login))
            .check(matches(hasTextILHint(LOGIN)))
    }

    @Test
    fun errorBackendTest() {
        val state = MutableStateFlow(SignInState.ErrorBackend(
            error = ERROR_STRING
        ))
        every {
            repository.signIn(any(), any())
        } returns state

        scenario = launchNavHiltFragment<SignFragment>(navController)

        onView(withId(R.id.bt_sign_in))
            .perform(click())

        onView(withId(R.id.pb_sign_in))
            .check(matches( not(isDisplayed())))
    }
    companion object {
        private const val FIELD_EMPTY = "Field is empty"
        private const val PASSWORD = "Password"
        private const val LOGIN = "Login"
        private const val ERROR_STRING = "error"
    }
}