package com.github.gunin_igor75.list_payments.di

import android.content.Context
import com.github.gunin_igor75.list_payments.presentation.MainActivity
import com.github.gunin_igor75.list_payments.presentation.home.HomeFragment
import com.github.gunin_igor75.list_payments.presentation.payments.ListPaymentFragment
import com.github.gunin_igor75.list_payments.presentation.signin.SignFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(signFragment: SignFragment)

    fun inject(listPaymentFragment: ListPaymentFragment)

    fun inject(homeFragment: HomeFragment)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance appContext: Context
        ): ApplicationComponent
    }
}