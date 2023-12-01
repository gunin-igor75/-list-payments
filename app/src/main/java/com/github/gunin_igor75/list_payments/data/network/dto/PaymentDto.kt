package com.github.gunin_igor75.list_payments.data.network.dto

import com.google.gson.annotations.SerializedName

data class PaymentDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("amount")
    val amount: String?,
    @SerializedName("created")
    val created: Long?
)
