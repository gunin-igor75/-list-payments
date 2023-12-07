package com.github.gunin_igor75.list_payments.data.network.dto

import com.google.gson.annotations.SerializedName

data class ResponseTokenDto(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("response")
    val response: TokenDto?,
    @SerializedName("error")
    val error: ErrorDto?
)