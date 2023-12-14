package com.github.gunin_igor75.list_payments.util

import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

open class ViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val testViewModelScopeRule = TestViewModelScopeRule()

    @get:Rule
    val mockkRule = MockKRule(this)
}