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
import com.example.pypypy.ui.screen.home.component.ProductItem
import com.example.pypypy.ui.screen.home.favourite.FavouriteScreenViewModel
import com.example.pypypy.ui.screen.home.sort.LikeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

class PopylarSneakersViewModel(private val authUseCase: AuthUseCase):ViewModel() {
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

}