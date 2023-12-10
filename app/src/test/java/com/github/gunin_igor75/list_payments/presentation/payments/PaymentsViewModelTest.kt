package com.github.gunin_igor75.list_payments.presentation.payments

import com.github.gunin_igor75.list_payments.domain.usecases.LoadPaymentsUseCase
import com.github.gunin_igor75.list_payments.util.ViewModelTest
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Test

class PaymentsViewModelTest: ViewModelTest(){

    @RelaxedMockK
    lateinit var loadPaymentsUseCase: LoadPaymentsUseCase

    @InjectMockKs
    lateinit var viewModel: PaymentsViewModel


    @Test
    fun loadPaymentsPositiveTest() {
        viewModel.loadPayments

        verify(exactly = 1) {
            loadPaymentsUseCase()
        }
    }
}