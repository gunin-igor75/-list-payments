package com.github.gunin_igor75.list_payments.domain.usecases

import com.github.gunin_igor75.list_payments.domain.repository.PaymentRepository
import javax.inject.Inject

class LoadPaymentsUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository
) {
    operator fun invoke() = paymentRepository.loadPayments()
}