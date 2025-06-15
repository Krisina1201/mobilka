package com.example.pypypy.ui.screen.home.popylar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pypypy.R
import com.example.pypypy.data.model.SnekersModel.PopularSneakersResponse
import com.example.pypypy.data.remote.NetworkResponse
import com.example.pypypy.data.remote.NetworkResponseSneakers
import com.example.pypypy.data.remote.NetworkResponseUser
import com.example.pypypy.domain.usecase.AuthUseCase
import com.example.pypypy.ui.screen.home.component.CartManager
import com.example.pypypy.ui.screen.home.component.CartModel
import com.example.pypypy.ui.screen.home.component.ProductItem
import com.example.pypypy.ui.screen.home.favourite.FavouriteScreenViewModel
import com.example.pypypy.ui.screen.home.garbage.GarbageScreenViewModel
import com.example.pypypy.ui.screen.home.sort.LikeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

class PopylarSneakersViewModel(private val treshView: CartManager,
    private val authUseCase: AuthUseCase):ViewModel() {
    private val _sneakersState =
        MutableStateFlow<NetworkResponseSneakers<List<PopularSneakersResponse>>>(
            NetworkResponseSneakers.Loading
        )
    val sneakersState: StateFlow<NetworkResponseSneakers<List<PopularSneakersResponse>>> =
        _sneakersState
    private val _state = MutableStateFlow<NetworkResponseUser<List<PopularSneakersResponse>>>(
        NetworkResponseUser.Loading
    )

    var signInState = mutableStateListOf(LikeModel())

    fun fetchSneakers() {
        viewModelScope.launch {
            authUseCase.popylar()
                .collect { response: NetworkResponseSneakers<List<PopularSneakersResponse>> ->
                    _sneakersState.value = response
                }
        }
    }

    fun allSneakers() {
        viewModelScope.launch {
            authUseCase.getSneakers()
                .collect { response: NetworkResponseSneakers<List<PopularSneakersResponse>> ->
                    _sneakersState.value = response
                }
        }
    }

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

    // В PopylarSneakersViewModel
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _history = MutableStateFlow<List<String>>(emptyList())
    val history: StateFlow<List<String>> = _history

    private val _searchResults = MutableStateFlow<NetworkResponseSneakers<List<PopularSneakersResponse>>>(NetworkResponseSneakers.Loading)
    val searchResults: StateFlow<NetworkResponseSneakers<List<PopularSneakersResponse>>> = _searchResults

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
        if (newQuery.isNotEmpty()) {
            searchSneakers(newQuery)
        } else {
            _searchResults.value = NetworkResponseSneakers.Success(emptyList())
        }
    }

    fun onItemClicked(item: String) {
        _query.value = item
        searchSneakers(item)
    }

    private fun searchSneakers(query: String) {
        viewModelScope.launch {
            _searchResults.value = NetworkResponseSneakers.Loading
            authUseCase.getSneakers()
                .collect { response ->
                    when (response) {
                        is NetworkResponseSneakers.Success -> {
                            val filtered = response.data.filter { sneaker ->
                                sneaker.productName.contains(query, ignoreCase = true) ||
                                        sneaker.category?.contains(query, ignoreCase = true) == true
                            }
                            _searchResults.value = NetworkResponseSneakers.Success(filtered)
                            updateHistory(query)
                        }
                        is NetworkResponseSneakers.Error -> {
                            _searchResults.value = response
                        }
                        NetworkResponseSneakers.Loading -> {
                            _searchResults.value = NetworkResponseSneakers.Loading
                        }
                    }
                }
        }
    }

    private fun updateHistory(query: String) {
        if (query.isNotBlank()) {
            val newHistory = _history.value.toMutableList().apply {
                remove(query)
                add(0, query)
                if (size > 5) removeLast()
            }
            _history.value = newHistory
        }
    }

    fun addToFavorite(id: Int) {
        viewModelScope.launch {
            // Реализация добавления в избранное
        }
    }

    fun removeFromFavorite(id: Int) {
        viewModelScope.launch {
            // Реализация удаления из избранного
        }
    }

}