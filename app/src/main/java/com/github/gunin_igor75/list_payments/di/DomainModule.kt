package com.github.gunin_igor75.list_payments.di

import com.github.gunin_igor75.list_payments.data.reposotory.PaymentRepositoryImp
import com.github.gunin_igor75.list_payments.domain.repository.PaymentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    @Singleton
    fun bindPaymentRepository(impl:PaymentRepositoryImp): PaymentRepository
}