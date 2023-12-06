package com.github.gunin_igor75.list_payments.domain.entity

import java.math.BigDecimal

data class Payment(
    val id: Long,
    val title: String,
    val amount: BigDecimal,
    val created: String
)
