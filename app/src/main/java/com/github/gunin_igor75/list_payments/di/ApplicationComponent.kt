package com.github.gunin_igor75.list_payments.di

import android.content.Context
import com.github.gunin_igor75.list_payments.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
        @BindsInstance appContext: Context
        ): ApplicationComponent
    }

}