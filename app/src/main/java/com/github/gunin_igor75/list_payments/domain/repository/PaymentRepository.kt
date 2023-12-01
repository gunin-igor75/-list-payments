package com.github.gunin_igor75.list_payments.domain.repository

import com.github.gunin_igor75.list_payments.domain.entity.Payment

interface PaymentRepository {

    suspend fun signIn(login: String, password: String): String

    suspend fun loadPayments(): List<Payment>

    fun logOut()
}