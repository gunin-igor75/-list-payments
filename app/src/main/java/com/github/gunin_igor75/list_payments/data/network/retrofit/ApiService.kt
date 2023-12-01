package com.github.gunin_igor75.list_payments.data.network.retrofit

import com.github.gunin_igor75.list_payments.data.network.dto.AccountDto
import com.github.gunin_igor75.list_payments.data.network.dto.ResponsePaymentsDto
import com.github.gunin_igor75.list_payments.data.network.dto.ResponseToken
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun signIn(@Body accountDto: AccountDto):ResponseToken

    @GET("payments")
    suspend fun getPayments():ResponsePaymentsDto
}