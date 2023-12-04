package com.github.gunin_igor75.list_payments.domain.entity

sealed class SignInState{
    data object Initialization: SignInState()
    data object Authorization: SignInState()

    data object ClearField: SignInState()
    data class Unauthorized(
        val error: String
    ): SignInState()
    data class EmptyField(
        val emptyLogin: Boolean = false,
        val emptyPassword: Boolean = false
    ): SignInState()
    data class NoConnection(
        val error: String
    ): SignInState()

}
