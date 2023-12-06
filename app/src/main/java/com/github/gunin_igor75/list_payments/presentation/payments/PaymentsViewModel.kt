package com.github.gunin_igor75.list_payments.presentation.payments

import androidx.lifecycle.ViewModel
import com.github.gunin_igor75.list_payments.domain.usecases.LoadPaymentsUseCase
import javax.inject.Inject

class PaymentsViewModel @Inject constructor(
    loadPaymentsUseCase: LoadPaymentsUseCase,
): ViewModel() {
    val loadPayments = loadPaymentsUseCase()
}