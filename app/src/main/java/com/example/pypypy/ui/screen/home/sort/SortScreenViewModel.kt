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
import com.example.pypypy.ui.screen.signUp.signin.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SortScreenViewModel(private val authUseCase: AuthUseCase): ViewModel() {
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