package com.github.gunin_igor75.list_payments.data.network.dto

import com.google.gson.annotations.SerializedName

data class ResponsePaymentsDto(
    @SerializedName("response")
    val payments: List<PaymentDto>
)
