package com.github.gunin_igor75.list_payments.data.network.dto

import com.google.gson.annotations.SerializedName

data class ErrorDto(
    @SerializedName("error_code")
    val errorCode:Int,
    @SerializedName("error_msg")
    val errorMsg: String
)
