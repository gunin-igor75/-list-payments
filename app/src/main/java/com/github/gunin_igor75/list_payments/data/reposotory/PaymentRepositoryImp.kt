package com.github.gunin_igor75.list_payments.data.reposotory

import com.github.gunin_igor75.list_payments.data.mapper.PaymentMapper
import com.github.gunin_igor75.list_payments.data.network.dto.AccountDto
import com.github.gunin_igor75.list_payments.data.network.retrofit.ApiService
import com.github.gunin_igor75.list_payments.domain.entity.Payment
import com.github.gunin_igor75.list_payments.domain.repository.PaymentRepository
import com.github.gunin_igor75.list_payments.domain.settings.TokenSettings
import javax.inject.Inject

class PaymentRepositoryImp @Inject constructor(
    private val tokenSettings: TokenSettings,
    private val mapper: PaymentMapper,
    private val apiService: ApiService
) : PaymentRepository {

    override suspend fun signIn(login: String, password: String): String {
        val accountDto = AccountDto(
            login = login,
            password = password
        )
        val token = apiService.signIn(accountDto).response.token
        tokenSettings.setCurrentToken(token)
        return token
    }

    override suspend fun loadPayments(): List<Payment> {
        return mapper.mapResponsePaymentsDtoToPayments(apiService.getPayments())
    }

    override fun logOut() {
        tokenSettings.setCurrentToken(null)
    }
}