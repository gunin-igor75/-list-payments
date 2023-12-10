package com.github.gunin_igor75.list_payments.presentation.home

import com.github.gunin_igor75.list_payments.domain.usecases.LogOutUseCase
import com.github.gunin_igor75.list_payments.util.ViewModelTest
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Test

class HomeViewModelTest : ViewModelTest() {

    @RelaxedMockK
    lateinit var logOutUseCase: LogOutUseCase

    @InjectMockKs
    lateinit var viewModel: HomeViewModel

    @Test
    fun logoutPositiveTest() {

        viewModel.logout()

        coVerify(exactly = 1) {
            logOutUseCase()
        }
    }
}