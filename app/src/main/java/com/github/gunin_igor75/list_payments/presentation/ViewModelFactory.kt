package com.github.gunin_igor75.list_payments.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.gunin_igor75.list_payments.di.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider

@ApplicationScope

class ViewModelFactory @Inject constructor(
    private val viewModelsProvider:
    @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelsProvider[modelClass]?.get() as T
    }
}