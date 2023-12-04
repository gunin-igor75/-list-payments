package com.github.gunin_igor75.list_payments.presentation.signin

import androidx.lifecycle.ViewModel
import com.github.gunin_igor75.list_payments.domain.entity.SignInState
import com.github.gunin_igor75.list_payments.domain.usecases.SignInUseCase
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
): ViewModel() {

    fun signIn(login: String, password: String): StateFlow<SignInState> {
        return signInUseCase(login, password)
    }
}