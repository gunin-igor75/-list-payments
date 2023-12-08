package com.github.gunin_igor75.list_payments.domain.entity

sealed class PaymentsState{
    data object Initialization: PaymentsState()
    data object Loading: PaymentsState()
    data class Content(
        val payments: List<Payment>
    ): PaymentsState()
    data class ErrorLoading(
        val error: String
    ): PaymentsState()
    data class ErrorBackend(
        val error: String
    ): PaymentsState()
}
