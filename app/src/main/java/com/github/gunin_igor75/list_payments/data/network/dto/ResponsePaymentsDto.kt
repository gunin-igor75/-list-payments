package com.github.gunin_igor75.list_payments.data.network.dto

import com.google.gson.annotations.SerializedName

data class ResponsePaymentsDto(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("response")
    val payments: List<PaymentDto>?,
    @SerializedName("error")
    val error: ErrorDto?
)
