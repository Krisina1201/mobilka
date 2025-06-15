package com.example.pypypy.ui.screen.home.sort

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pypypy.R
import com.example.pypypy.data.model.SnekersModel.PopularSneakersResponse
import com.example.pypypy.data.remote.NetworkResponse
import com.example.pypypy.data.remote.NetworkResponseSneakers
import com.example.pypypy.data.remote.NetworkResponseUser
import com.example.pypypy.domain.usecase.AuthUseCase
import com.example.pypypy.ui.screen.home.component.CartManager
import com.example.pypypy.ui.screen.home.component.CartModel
import com.example.pypypy.ui.screen.home.garbage.GarbageScreenViewModel
import com.example.pypypy.ui.screen.signUp.signin.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SortScreenViewModel(private val treshView: CartManager,
    private val authUseCase: AuthUseCase): ViewModel() {
    var signInState = mutableStateListOf(LikeModel())

    fun addToFavourite(shoeId: Int) {
        viewModelScope.launch {
            var result = authUseCase.postFav(shoeId = shoeId)


            result.collect {
                when(it) {
                    is NetworkResponse.Error -> {

                    }

                    is NetworkResponse.Success<*> -> {

                    }

                    is NetworkResponse.Loading -> {

                    }
                }
            }
        }
    }

    var cartState = mutableStateListOf(CartModel())

    fun addToCart(shoeId: Int) {
        viewModelScope.launch {
            val existingItem = cartState.firstOrNull { it.shoeId == shoeId }

            if (existingItem != null) {
                // Товар уже в корзине — увеличиваем количество
                authUseCase.addFromBasket(shoeId, 1).collect { response ->
                    if (response is NetworkResponse.Success<*> && response.data == true) {
                        val index = cartState.indexOfFirst { it.shoeId == shoeId }
                        cartState[index] = existingItem.copy(quantity = existingItem.quantity + 1)
                        treshView.notifyCartUpdated()
                    }
                }
            } else {
                // Товара нет в корзине — добавляем
                authUseCase.addFromBasket(shoeId, 1).collect { response ->
                    if (response is NetworkResponse.Success<*> && response.data == true) {
                        cartState.add(CartModel(shoeId, true, 1))
                        treshView.notifyCartUpdated()
                    }
                }
            }
        }
    }

    fun removeFromCart(shoeId: Int) {
        viewModelScope.launch {
            authUseCase.deleteFromBasket(shoeId, 1).collect { response ->
                if (response is NetworkResponse.Success<*> && response.data == true) {
                    // Обновляем локальное состояние
                    val index = cartState.indexOfFirst { it.shoeId == shoeId }
                    if (index != -1) {
                        cartState[index] = cartState[index].copy(inCart = false)
                    }
                }
            }
        }
    }

    init {
        loadCartState()
    }

    private fun loadCartState() {
        viewModelScope.launch {
            authUseCase.getBasket().collect { response ->
                when (response) {
                    is NetworkResponseSneakers.Success -> {
                        cartState.clear()
                        response.data.forEach { item ->
                            cartState.add(CartModel(item.sneakers.id, true, item.countInBasket))
                        }
                    }
                    is NetworkResponseSneakers.Error -> { /* Обработка ошибки */ }
                    else -> {}
                }
            }
        }
    }

    fun deleteFromFavourite(shoeId: Int) {
        viewModelScope.launch {
            var result = authUseCase.deleteFav(shoeId = shoeId)


            result.collect {
                when(it) {
                    is NetworkResponse.Error -> {

                    }

                    is NetworkResponse.Success<*> -> {

                    }

                    is NetworkResponse.Loading -> {

                    }
                }
            }
        }
    }

}