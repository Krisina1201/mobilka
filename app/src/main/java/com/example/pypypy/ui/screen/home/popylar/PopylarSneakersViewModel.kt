package com.example.pypypy.ui.screen.home.popylar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pypypy.data.model.SnekersModel.PopularSneakersResponse
import com.example.pypypy.data.remote.NetworkResponseSneakers
import com.example.pypypy.domain.usecase.AuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PopylarSneakersViewModel(private val authUseCase: AuthUseCase):ViewModel() {
    private val _sneakersState = MutableStateFlow<NetworkResponseSneakers<List<PopularSneakersResponse>>>(NetworkResponseSneakers.Loading)
    val sneakersState: StateFlow<NetworkResponseSneakers<List<PopularSneakersResponse>>> = _sneakersState

    fun fetchSneakers() {
        viewModelScope.launch {
            authUseCase.popylar().collect { response: NetworkResponseSneakers<List<PopularSneakersResponse>> ->
                _sneakersState.value = response
            }
        }
    }

    fun allSneakers() {
        viewModelScope.launch {
            authUseCase.getSneakers().collect { response: NetworkResponseSneakers<List<PopularSneakersResponse>> ->
                _sneakersState.value = response
            }
        }
    }
}