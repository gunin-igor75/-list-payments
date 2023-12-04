package com.github.gunin_igor75.list_payments.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.gunin_igor75.list_payments.domain.usecases.LogOutUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase
): ViewModel() {

    fun logout(){
        viewModelScope.launch {
            logOutUseCase()
        }
    }
}