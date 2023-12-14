package com.github.gunin_igor75.list_payments.presentation.payments

import androidx.lifecycle.ViewModel
import com.github.gunin_igor75.list_payments.domain.entity.PaymentsState
import com.github.gunin_igor75.list_payments.domain.usecases.LoadPaymentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(
    private val loadPaymentsUseCase: LoadPaymentsUseCase,
): ViewModel() {
    fun loadPayments(): StateFlow<PaymentsState> {
        return loadPaymentsUseCase()
    }
}