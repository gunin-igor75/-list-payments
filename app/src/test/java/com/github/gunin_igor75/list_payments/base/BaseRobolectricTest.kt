package com.github.gunin_igor75.list_payments.base

import com.github.gunin_igor75.list_payments.domain.repository.PaymentRepository
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

open class BaseRobolectricTest: BaseTest() {

    @get: Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: PaymentRepository

    @Before
    open fun setup() {
        hiltRule.inject()
    }
}