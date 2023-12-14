package com.github.gunin_igor75.list_payments.di

import com.github.gunin_igor75.list_payments.data.network.retrofit.ApiFactory
import com.github.gunin_igor75.list_payments.data.network.retrofit.ApiService
import com.github.gunin_igor75.list_payments.data.settings.SharedPreferencesToken
import com.github.gunin_igor75.list_payments.domain.settings.TokenSettings
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindTokenSettings(impl: SharedPreferencesToken): TokenSettings

    companion object{

        @Provides
        @Singleton
        fun provideApiService(apiFactory: ApiFactory): ApiService {
            return apiFactory.apiService
        }
    }
}