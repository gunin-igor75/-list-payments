package com.github.gunin_igor75.list_payments.presentation.signin

import com.github.gunin_igor75.list_payments.domain.usecases.SignInUseCase
import com.github.gunin_igor75.list_payments.util.ViewModelTest
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Test

class SignInViewModelTest: ViewModelTest(){

    @RelaxedMockK
    lateinit var signInUseCase: SignInUseCase

    @InjectMockKs
    lateinit var viewModel: SignInViewModel

    @Test
    fun signInPositiveTest() {
        viewModel.signIn("demo", "12345")

        verify(exactly = 1) {
            signInUseCase(any(), any())
        }
    }
}