package com.github.gunin_igor75.list_payments.data.mapper

import com.github.gunin_igor75.list_payments.data.network.dto.PaymentDto
import com.github.gunin_igor75.list_payments.data.network.dto.ResponsePaymentsDto
import com.github.gunin_igor75.list_payments.data.network.dto.ResponseTokenDto
import com.github.gunin_igor75.list_payments.domain.entity.ErrorData
import com.github.gunin_igor75.list_payments.domain.entity.Payment
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class PaymentMapper @Inject constructor() {
    fun mapResponsePaymentsDtoToPayments(responsePaymentsDto: ResponsePaymentsDto): List<Payment> {
        val paymentDtos = responsePaymentsDto.payments
        return paymentDtos.map {
            mapPaymentDtoToPayment(it)
        }
    }

    fun mapResponseTokenToErrorData(responseTokenDto: ResponseTokenDto): ErrorData {
        val error = responseTokenDto.error
        return ErrorData(
            message = error.errorMsg
        )
    }

    fun mapResponsePaymentsDtoToErrorData(paymentsDto: ResponsePaymentsDto): ErrorData {
        val error = paymentsDto.error
        return ErrorData(
            message = error.errorMsg
        )
    }
    private fun mapPaymentDtoToPayment(paymentDto: PaymentDto): Payment {
        return Payment(
            id = paymentDto.id,
            title = paymentDto.title,
            amount = convertorStringToNumber(paymentDto.amount),
            created = convertTime(paymentDto.created)
        )
    }

    private fun convertorStringToNumber(s: String?): BigDecimal {
        val temp = s?.toBigDecimalOrNull()?: BigDecimal.ZERO
        return temp.setScale(2)
    }

    private fun convertTime(date: Long?): String {
        if (date == null) return ""
        val currentDate = Date(date * 1000)
        val pattern = "d MMMM , E"
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        formatter.timeZone = TimeZone.getDefault()
        return formatter.format(currentDate)
    }
}