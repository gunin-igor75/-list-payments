package com.github.gunin_igor75.list_payments.util

import com.github.gunin_igor75.list_payments.data.network.dto.ErrorDto
import com.github.gunin_igor75.list_payments.data.network.dto.PaymentDto
import com.github.gunin_igor75.list_payments.data.network.dto.ResponsePaymentsDto
import com.github.gunin_igor75.list_payments.data.network.dto.ResponseTokenDto
import com.github.gunin_igor75.list_payments.domain.entity.ErrorData
import com.github.gunin_igor75.list_payments.domain.entity.Payment
import java.math.BigDecimal

const val TOKEN = "7b7c0a690bee2e8d90512ed1b57e19f0"

fun getPaymentListDto(): List<PaymentDto> {
    return listOf(
        PaymentDto(
            id = 1,
            title = "Test Payment 1",
            amount = "101",
            created = 1683904809
        ),
        PaymentDto(
            id = 2,
            title = "Test Payment 2",
            amount = "101",
            created = 1684307212
        ),
        PaymentDto(
            id = 3,
            title = "Test Payment 3",
            amount = "101.50",
            created = 1684312723
        ),
        PaymentDto(
            id = 4,
            title = "Test Payment 4",
            amount = "100000000.05",
            created = 1684581497
        ),
        PaymentDto(
            id = 5,
            title = "Test Payment 5",
            amount = "",
            created = 1684741518
        ),
        PaymentDto(
            id = 6,
            title = "Test Payment 6",
            amount = null,
            created = 1684743754
        ),

        PaymentDto(
            id = 7,
            title = "Test Payment 7",
            amount = null,
            created = null
        ),
    )
}

fun getListPayment(): List<Payment> {
    return listOf(
        Payment(
            id = 1,
            title = "Test Payment 1",
            amount = BigDecimal("101.00"),
            created = "12 мая , пт"
        ),
        Payment(
            id = 2,
            title = "Test Payment 2",
            amount = BigDecimal("101.00"),
            created = "17 мая , ср"
        ),
        Payment(
            id = 3,
            title = "Test Payment 3",
            amount = BigDecimal("101.50"),
            created = "17 мая , ср"
        ),
        Payment(
            id = 4,
            title = "Test Payment 4",
            amount = BigDecimal("100000000.05"),
            created = "20 мая , сб"
        ),
        Payment(
            id = 5,
            title = "Test Payment 5",
            amount = BigDecimal("0.00"),
            created = "22 мая , пн"
        ),
        Payment(
            id = 6,
            title = "Test Payment 6",
            amount = BigDecimal("0.00"),
            created = "22 мая , пн"
        ),

        Payment(
            id = 7,
            title = "Test Payment 7",
            amount = BigDecimal("0.00"),
            created = ""
        ),
    )
}

fun getErrorDto(): ErrorDto {
    return ErrorDto(
        errorCode = 401,
        errorMsg = "error"
    )
}

fun getResponseTokenDto(): ResponseTokenDto {
    return ResponseTokenDto(
        success = false,
        response = null,
        error = getErrorDto()
    )
}

fun getResponseTokenDtoErrorDataNull(): ResponseTokenDto {
    return ResponseTokenDto(
        success = false,
        response = null,
        error = null
    )
}


fun getErrorData(): ErrorData {
    return ErrorData(
        message = "error"
    )
}

fun getResponsePaymentsDto(): ResponsePaymentsDto {
    return ResponsePaymentsDto(
        success = false,
        payments = null,
        error = getErrorDto()
    )
}

fun getResponsePaymentsDtoErrorDataNull(): ResponsePaymentsDto {
    return ResponsePaymentsDto(
        success = false,
        payments = null,
        error = null
    )
}

