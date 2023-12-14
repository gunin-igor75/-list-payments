package com.github.gunin_igor75.list_payments.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.gunin_igor75.list_payments.domain.repository.PaymentRepository
import com.github.gunin_igor75.list_payments.utils.rules.TestAndroidViewModelScopeRule
import dagger.hilt.android.testing.HiltAndroidRule
import io.mockk.junit4.MockKRule
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

open class BaseTest {

    @get: Rule
    val testAndroidViewModelScopeRule = TestAndroidViewModelScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val mockKRule = MockKRule(this)

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: PaymentRepository

    @Before
    open fun setup() {
        hiltRule.inject()
    }
}