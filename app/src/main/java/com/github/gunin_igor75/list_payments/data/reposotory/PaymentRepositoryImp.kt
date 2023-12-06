package com.github.gunin_igor75.list_payments.data.reposotory

import android.content.Context
import android.util.Log
import com.github.gunin_igor75.list_payments.R
import com.github.gunin_igor75.list_payments.data.mapper.PaymentMapper
import com.github.gunin_igor75.list_payments.data.network.dto.AccountDto
import com.github.gunin_igor75.list_payments.data.network.retrofit.ApiService
import com.github.gunin_igor75.list_payments.domain.entity.PaymentsState
import com.github.gunin_igor75.list_payments.domain.entity.SignInState
import com.github.gunin_igor75.list_payments.domain.entity.SignInState.EmptyField
import com.github.gunin_igor75.list_payments.domain.entity.SignInState.Initialization
import com.github.gunin_igor75.list_payments.domain.repository.PaymentRepository
import com.github.gunin_igor75.list_payments.domain.settings.TokenSettings
import com.github.gunin_igor75.list_payments.exstensions.mergeWith
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class PaymentRepositoryImp @Inject constructor(
    private val tokenSettings: TokenSettings,
    private val mapper: PaymentMapper,
    private val apiService: ApiService,
    private val context: Context
) : PaymentRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val clearField = MutableSharedFlow<SignInState>()
    override fun signIn(login: String, password: String) = flow {
        if (login.isBlank()) {
            emit(EmptyField(emptyLogin = true))
            return@flow
        }
        if (password.isBlank()) {
            emit(EmptyField(emptyPassword = true))
            return@flow
        }
        val accountDto = AccountDto(login, password)
        val responseToken = apiService.signIn(accountDto)
        if (responseToken.success) {
            val token = responseToken.response.token
            tokenSettings.setCurrentToken(token)
            emit(SignInState.Authorization)
        } else {
            val errorData = mapper.mapResponseTokenToErrorData(responseToken)
            emit(SignInState.Unauthorized(errorData.message))
        }
    }.onStart { emit(SignInState.Loading) }
        .catch { e ->
            val message = e.message ?: context.getString(R.string.connection_error)
            emit(
                SignInState.NoConnection(
                    error = message
                )
            )
            Log.d(TAG, message)
        }.mergeWith(clearField)
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = Initialization
        )


    override fun loadPayments() = flow {
        val responsePaymentsDto = apiService.getPayments()
        if (responsePaymentsDto.success) {
            emit(
                PaymentsState.PaymentsListState(
                    payments = mapper.mapResponsePaymentsDtoToPayments(responsePaymentsDto)
                )
            )
        } else {
            val errorData = mapper.mapResponsePaymentsDtoToErrorData(responsePaymentsDto)
            emit(
                PaymentsState.ErrorLoading(
                    error = errorData.message
                )
            )
        }
    }.onStart { emit(PaymentsState.Loading) }
        .retry(2) {
            delay(TIME_OUT_RETRY)
            true
        }
        .catch { e ->
            val message = e.message ?: context.getString(R.string.connection_error)
            emit(
                PaymentsState.NoConnection(
                    error = message
                )
            )
            Log.d(TAG, message)
        }
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = PaymentsState.Initialization
        )

    override suspend fun logOut() {
        tokenSettings.setCurrentToken(null)
        clearField.emit(SignInState.ClearField)
    }

    companion object {
        private const val TIME_OUT_RETRY = 500L
        private const val TAG = "PaymentRepositoryImp"
    }
}