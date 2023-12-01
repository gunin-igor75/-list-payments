package com.github.gunin_igor75.list_payments.domain.settings

interface TokenSettings {

    fun setCurrentToken(token: String?)

    fun getCurrentToken():String?
}