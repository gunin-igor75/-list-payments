package com.github.gunin_igor75.list_payments.di

import androidx.lifecycle.ViewModel
import com.github.gunin_igor75.list_payments.presentation.home.HomeViewModel
import com.github.gunin_igor75.list_payments.presentation.payments.PaymentsViewModel
import com.github.gunin_igor75.list_payments.presentation.signin.SignInViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    @Binds
    fun bindSignInViewModel(signInViewModel: SignInViewModel): ViewModel

    @IntoMap
    @ViewModelKey(PaymentsViewModel::class)
    @Binds
    fun bindPaymentsViewModel(paymentsViewModel: PaymentsViewModel): ViewModel

    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    @Binds
    fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel
}