package com.github.gunin_igor75.list_payments.data.network.dto

import com.google.gson.annotations.SerializedName

data class ResponseToken(
    @SerializedName("response")
    val response: TokenDto
)