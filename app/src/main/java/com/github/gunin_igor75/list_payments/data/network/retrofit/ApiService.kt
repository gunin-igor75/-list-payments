package com.github.gunin_igor75.list_payments.data.network.retrofit

import com.github.gunin_igor75.list_payments.data.network.dto.AccountDto
import com.github.gunin_igor75.list_payments.data.network.dto.ResponsePaymentsDto
import com.github.gunin_igor75.list_payments.data.network.dto.ResponseTokenDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun signIn(@Body accountDto: AccountDto):ResponseTokenDto

    @GET("payments")
    suspend fun getPayments():ResponsePaymentsDto
}