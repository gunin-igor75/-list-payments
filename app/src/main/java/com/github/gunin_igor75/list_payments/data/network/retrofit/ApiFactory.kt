package com.github.gunin_igor75.list_payments.data.network.retrofit

import com.github.gunin_igor75.list_payments.domain.settings.TokenSettings
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

data class ApiFactory @Inject constructor(
    private val tokenSettings: TokenSettings
) {
    private val token: String?
        get() = tokenSettings.getCurrentToken()

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(createAuthorizationInterceptor())
        .addInterceptor(loggingInterceptor())
        .build()


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)

    private fun createAuthorizationInterceptor(): Interceptor {
        return Interceptor { chain ->
            val newBuilder = chain.request().newBuilder()
            newBuilder.addHeader(APP_KEY, VALUE_APP_KEY)
            newBuilder.addHeader(V, VALUE_V)
            val  currentToken = token
            if (currentToken != null) {
                newBuilder.addHeader(TOKEN, currentToken)
            }
            return@Interceptor chain.proceed(newBuilder.build())
        }
    }

    private fun loggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    companion object {
        private const val V = "v"
        private const val APP_KEY = "app-key"
        private const val TOKEN = "token"
        private const val VALUE_APP_KEY = "12345"
        private const val VALUE_V = "1"
        private const val BASE_URL = "https://easypay.world/api-test/"
    }
}