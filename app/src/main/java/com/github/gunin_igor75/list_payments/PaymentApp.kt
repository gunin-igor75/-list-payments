package com.github.gunin_igor75.list_payments

import android.app.Application
import com.github.gunin_igor75.list_payments.di.DaggerApplicationComponent

class PaymentApp: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }
}