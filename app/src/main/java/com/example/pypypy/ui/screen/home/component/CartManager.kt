package com.example.pypypy.ui.screen.home.component

import androidx.lifecycle.ViewModel
import com.example.pypypy.domain.usecase.AuthUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class CartManager(private val authUseCase: AuthUseCase): ViewModel() {
    private val _cartUpdates = MutableSharedFlow<Unit>()
    val cartUpdates = _cartUpdates.asSharedFlow()

    suspend fun notifyCartUpdated() {
        _cartUpdates.emit(Unit)
    }
}