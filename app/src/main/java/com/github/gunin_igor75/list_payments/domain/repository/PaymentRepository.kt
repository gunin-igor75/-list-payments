package com.github.gunin_igor75.list_payments.domain.repository

import com.github.gunin_igor75.list_payments.domain.entity.PaymentsState
import com.github.gunin_igor75.list_payments.domain.entity.SignInState
import kotlinx.coroutines.flow.StateFlow

interface PaymentRepository {

    fun signIn(login: String, password: String): StateFlow<SignInState>

    fun loadPayments(): StateFlow<PaymentsState>

    suspend fun logOut()
}