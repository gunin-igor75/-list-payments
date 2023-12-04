package com.github.gunin_igor75.list_payments.di

import com.github.gunin_igor75.list_payments.data.network.retrofit.ApiFactory
import com.github.gunin_igor75.list_payments.data.network.retrofit.ApiService
import com.github.gunin_igor75.list_payments.data.reposotory.PaymentRepositoryImp
import com.github.gunin_igor75.list_payments.domain.repository.PaymentRepository
import com.github.gunin_igor75.list_payments.domain.settings.SharedPreferencesToken
import com.github.gunin_igor75.list_payments.domain.settings.TokenSettings
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindTokenSettings(impl: SharedPreferencesToken): TokenSettings

    @ApplicationScope
    @Binds
    fun bindPaymentRepository(impl: PaymentRepositoryImp): PaymentRepository

    companion object {
        @ApplicationScope
        @Provides
        fun provideApiService(apiFactory: ApiFactory): ApiService {
            return apiFactory.apiService
        }
    }
}