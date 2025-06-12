// GarbageScreenViewModel.kt
package com.example.pypypy.ui.screen.home.garbage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pypypy.data.model.SnekersModel.SneakersInBasket
import com.example.pypypy.data.remote.NetworkResponse
import com.example.pypypy.data.remote.NetworkResponseSneakers
import com.example.pypypy.domain.usecase.AuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GarbageScreenViewModel(private val authUseCase: AuthUseCase) : ViewModel() {

    private val _sneakersState = MutableStateFlow<NetworkResponseSneakers<List<SneakersInBasket>>>(
        NetworkResponseSneakers.Loading
    )
    val sneakersState: StateFlow<NetworkResponseSneakers<List<SneakersInBasket>>> = _sneakersState

    private val _totalPrice = MutableStateFlow(0)
    val totalPrice: StateFlow<Int> = _totalPrice

    private val _deliveryPrice = MutableStateFlow(60)
    val deliveryPrice: StateFlow<Int> = _deliveryPrice

    private val _finalPrice = MutableStateFlow(0)
    val finalPrice: StateFlow<Int> = _finalPrice

    init {
        loadBasket()
    }

    fun loadBasket() {
        viewModelScope.launch {
            authUseCase.getBasket()
                .collect { response ->
                    _sneakersState.value = response
                    if (response is NetworkResponseSneakers.Success) {
                        calculateTotal(response.data)
                    }
                }
        }
    }

    fun updateQuantity(shoeId: Int) {
        viewModelScope.launch {
            authUseCase.addFromBasket(shoeId, 1).collect { response ->
                if (response is NetworkResponse.Success<*> && response.data == true) {
                    loadBasket()
                }
            }
        }
    }

    fun minusQuantity(shoeId: Int) {
        viewModelScope.launch {
            val result = authUseCase.deleteFromBasket(shoeId, 1)

            result.collect {
                when (it) {
                    is NetworkResponse.Error -> {
                        println(it.errorMessage)
                    }

                    is NetworkResponse.Success<*> -> {
                        loadBasket()
                    }

                    is NetworkResponse.Loading -> {

                    }
                }
            }
        }
    }

    fun deleteFromBasket(shoeId: Int, count: Int) {
        viewModelScope.launch {
            authUseCase.deleteFromBasket(shoeId, count).collect { response ->
                if (response is NetworkResponse.Success<*> && response.data == true) {
                    loadBasket()
                }
            }
        }
    }

    private fun calculateTotal(items: List<SneakersInBasket>) {
        val total = items.sumOf {
            val price = it.sneakers.cost.toIntOrNull() ?: 0
            price * it.countInBasket
        }
        _totalPrice.value = total
        _finalPrice.value = total + _deliveryPrice.value
    }
}