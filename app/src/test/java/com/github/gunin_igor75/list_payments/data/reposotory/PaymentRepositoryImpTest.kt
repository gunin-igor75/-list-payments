package com.github.gunin_igor75.list_payments.data.reposotory

import android.content.Context
import android.util.Log
import app.cash.turbine.test
import com.github.gunin_igor75.list_payments.data.mapper.PaymentMapper
import com.github.gunin_igor75.list_payments.data.network.retrofit.ApiService
import com.github.gunin_igor75.list_payments.domain.entity.PaymentsState
import com.github.gunin_igor75.list_payments.domain.entity.SignInState
import com.github.gunin_igor75.list_payments.domain.repository.PaymentRepository
import com.github.gunin_igor75.list_payments.domain.settings.TokenSettings
import com.github.gunin_igor75.list_payments.util.EMPTY_STRING
import com.github.gunin_igor75.list_payments.util.MESSAGE_ERROR_SERVER
import com.github.gunin_igor75.list_payments.util.getResponsePaymentsDto
import com.github.gunin_igor75.list_payments.util.getResponsePaymentsDtoErrorData
import com.github.gunin_igor75.list_payments.util.getResponsePaymentsDtoErrorDataNull
import com.github.gunin_igor75.list_payments.util.getResponseTokenDto
import com.github.gunin_igor75.list_payments.util.getResponseTokenDtoError
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PaymentRepositoryImpTest {

    @get:Rule
    val rule = MockKRule(this)

    @RelaxedMockK
    lateinit var tokenSettings: TokenSettings

    @MockK
    lateinit var context: Context

    @RelaxedMockK
    lateinit var apiService: ApiService

    private lateinit var mapper: PaymentMapper

    private lateinit var repository: PaymentRepository


    @Before
    fun setup() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        mapper = PaymentMapper()
        repository = PaymentRepositoryImp(
            tokenSettings = tokenSettings,
            mapper = mapper,
            context = context,
            apiService = apiService,
        )
    }

    @Test
    fun signInPositiveEmptyPasswordTest() = runTest {
        val flow = repository.signIn("demo", "")
        flow.test {
            assertThat(SignInState.Initialization).isEqualTo(awaitItem())
            assertThat(SignInState.Loading).isEqualTo(awaitItem())
            assertThat(
                SignInState.EmptyField(
                    emptyPassword = true
                )
            ).isEqualTo(awaitItem())
        }
    }

    @Test
    fun signInPositiveEmptyLoginTest() = runTest {
        val flow = repository.signIn("", "12345")
        flow.test {
            assertThat(SignInState.Initialization).isEqualTo(awaitItem())
            assertThat(SignInState.Loading).isEqualTo(awaitItem())
            assertThat(
                SignInState.EmptyField(
                    emptyLogin = true
                )
            ).isEqualTo(awaitItem())
        }
    }

    @Test
    fun signInPositiveAuthorizationTest() = runTest {
        coEvery {
            apiService.signIn(any())
        } returns getResponseTokenDto()

        val flow = repository.signIn("demo", "12345")

        flow.test {
            assertThat(SignInState.Initialization).isEqualTo(awaitItem())
            assertThat(SignInState.Loading).isEqualTo(awaitItem())
            assertThat(SignInState.Authorization).isEqualTo(awaitItem())
        }
    }

    @Test
    fun signInPositiveUnauthorizedTest() = runTest {
        coEvery {
            apiService.signIn(any())
        } returns getResponseTokenDtoError()

        val flow = repository.signIn("demo", "123456")
        flow.test {
            assertThat(SignInState.Initialization).isEqualTo(awaitItem())
            assertThat(SignInState.Loading).isEqualTo(awaitItem())
            assertThat(
                SignInState.Unauthorized(
                    error = "error"
                )
            ).isEqualTo(awaitItem())
        }
    }

    @Test
    fun signInNegativeTokenIsNullTest() = runTest {
        val responseTokenDto = getResponseTokenDto().copy(response = null)
        coEvery {
            apiService.signIn(any())
        } returns responseTokenDto

        every {
            context.getString(any())
        } returns MESSAGE_ERROR_SERVER

        val flow = repository.signIn("demo", "12345")
        flow.test {
            assertThat(SignInState.Initialization).isEqualTo(awaitItem())
            assertThat(SignInState.Loading).isEqualTo(awaitItem())
            assertThat(
                SignInState.ErrorBackend(
                    error = MESSAGE_ERROR_SERVER
                )
            ).isEqualTo(awaitItem())
        }
    }

    @Test
    fun loadPaymentsPositiveContentTest() = runTest {
        coEvery {
            apiService.getPayments()
        } returns getResponsePaymentsDto()

        val flow = repository.loadPayments()
        flow.test {
            assertThat(PaymentsState.Initialization).isEqualTo(awaitItem())
            assertThat(PaymentsState.Loading).isEqualTo(awaitItem())
            assertThat(
                PaymentsState.Content(
                    payments = mapper.mapResponsePaymentsDtoToPayments(getResponsePaymentsDto())
                )
            ).isEqualTo(awaitItem())
        }
    }

    @Test
    fun loadPaymentsPositiveErrorLoadingTest() = runTest {
        val response = getResponsePaymentsDtoErrorData()
        coEvery {
            apiService.getPayments()
        } returns response

        val flow = repository.loadPayments()
        flow.test {
            assertThat(PaymentsState.Initialization).isEqualTo(awaitItem())
            assertThat(PaymentsState.Loading).isEqualTo(awaitItem())
            assertThat(
                PaymentsState.ErrorLoading(
                    error = response.error?.errorMsg ?: EMPTY_STRING
                )
            ).isEqualTo(awaitItem())
        }
    }

    @Test
    fun loadPaymentsNegativeErrorBackendTest() = runTest {
        coEvery {
            apiService.getPayments()
        } returns getResponsePaymentsDtoErrorDataNull()

        val flow = repository.loadPayments()
        flow.test {
            assertThat(PaymentsState.Initialization).isEqualTo(awaitItem())
            assertThat(PaymentsState.Loading).isEqualTo(awaitItem())
            assertThat(
                PaymentsState.ErrorBackend(
                    error = "Response no success error is null"
                )
            ).isEqualTo(awaitItem())
        }
    }

    @Test
    fun logOutPositiveTest() = runTest {
        val flow = repository.signIn("", "12345")
        flow.test {
            assertThat(SignInState.Initialization).isEqualTo(awaitItem())
            assertThat(SignInState.Loading).isEqualTo(awaitItem())
            assertThat(
                SignInState.EmptyField(
                    emptyLogin = true
                )
            ).isEqualTo(awaitItem())
        }
        repository.logOut()
        flow.test {
            assertThat(SignInState.ClearField).isEqualTo(awaitItem())
        }
        coVerify {
            tokenSettings.setCurrentToken(null)
        }
    }
}