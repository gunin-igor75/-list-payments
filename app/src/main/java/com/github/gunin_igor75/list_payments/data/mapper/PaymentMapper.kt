package com.github.gunin_igor75.list_payments.data.mapper

import com.github.gunin_igor75.list_payments.data.network.dto.PaymentDto
import com.github.gunin_igor75.list_payments.data.network.dto.ResponsePaymentsDto
import com.github.gunin_igor75.list_payments.domain.entity.Payment
import java.math.BigDecimal
import javax.inject.Inject

class PaymentMapper @Inject constructor() {


    fun mapResponsePaymentsDtoToPayments(responsePaymentsDto: ResponsePaymentsDto): List<Payment> {
        val paymentDtos = responsePaymentsDto.payments
        return paymentDtos.map {
            mapPaymentDtoToPayment(it)
        }
    }

    private fun mapPaymentDtoToPayment(paymentDto: PaymentDto): Payment {
        return Payment(
            id = paymentDto.id,
            title = paymentDto.title,
            amount = convertorStringToNumber(paymentDto.amount),
            created = paymentDto.created ?: 0
        )
    }

    private fun convertorStringToNumber(s: String?): BigDecimal {
        val temp = s?.toBigDecimalOrNull()?: BigDecimal.ZERO
        return temp.setScale(2)
    }
}