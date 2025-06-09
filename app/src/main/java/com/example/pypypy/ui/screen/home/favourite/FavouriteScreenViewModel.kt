package com.example.pypypy.ui.screen.home.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pypypy.data.model.SignInModel.UserResponce
import com.example.pypypy.data.model.SnekersModel.PopularSneakersResponse
import com.example.pypypy.data.remote.NetworkResponse
import com.example.pypypy.data.remote.NetworkResponseSneakers
import com.example.pypypy.data.remote.NetworkResponseUser
import com.example.pypypy.domain.usecase.AuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavouriteScreenViewModel(private val authUseCase: AuthUseCase): ViewModel() {
    private val _state = MutableStateFlow<NetworkResponseUser<List<PopularSneakersResponse>>>(
        NetworkResponseUser.Loading)
    val userState: StateFlow<NetworkResponseUser<List<PopularSneakersResponse>>> = _state

    fun getFavourite() {
        viewModelScope.launch {
            authUseCase.getUser().collect {
                response: NetworkResponseUser<List<PopularSneakersResponse>> ->
                _state.value = response
            }
        }
    }
}