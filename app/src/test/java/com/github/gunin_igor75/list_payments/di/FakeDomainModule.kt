package com.github.gunin_igor75.list_payments.di

import com.github.gunin_igor75.list_payments.domain.repository.PaymentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.mockk.mockk
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface FakeDomainModule {

    companion object{

        @Provides
        @Singleton
        fun provideFakePaymentRepository(): PaymentRepository {
            return mockk()
        }
    }
}