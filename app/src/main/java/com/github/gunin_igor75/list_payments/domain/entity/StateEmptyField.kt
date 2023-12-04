package com.github.gunin_igor75.list_payments.domain.entity

sealed class StateEmptyField{
    object Initialization: StateEmptyField()
    object EmptyLogin: StateEmptyField()
    object EmptyPassword: StateEmptyField()
}
