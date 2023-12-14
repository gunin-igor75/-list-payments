package com.github.gunin_igor75.list_payments.base

import com.github.gunin_igor75.list_payments.base.rule.TesDispatcherRule
import io.mockk.junit4.MockKRule
import org.junit.Rule

open class BaseTest {

    @get: Rule
    val mockKRule = MockKRule(this)

    @get: Rule
    val dispatcherRule = TesDispatcherRule()

}