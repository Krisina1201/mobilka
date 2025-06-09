package com.example.pypypy.ui.screen.signUp.signin

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pypypy.data.model.SignInModel.AuthRequest
import com.example.pypypy.data.remote.NetworkResponse
import com.example.pypypy.domain.usecase.AuthUseCase
import kotlinx.coroutines.launch

class SignInViewModel(val authUseCase: AuthUseCase): ViewModel() {
    var signInState = mutableStateOf(SignInState())
        private set
    val emailHasError = derivedStateOf {
        if(signInState.value.email.isEmpty()) return@derivedStateOf false
        !android.util.Patterns.EMAIL_ADDRESS.matcher(signInState.value.email).matches()
    }

    fun setEmail(email: String){
        signInState.value = signInState.value.copy(email = email)
    }
    fun setPassword(password: String){
        signInState.value = signInState.value.copy(password = password)
    }

    private fun setErrorMassage(message: String) {
        signInState.value = signInState.value.copy(errorMessage = message)
    }

    private fun setLoading(isLoading: Boolean) {
        signInState.value = signInState.value.copy(isLoading = isLoading)
    }

    fun sighIn() {
        viewModelScope.launch {
            val authRequest = AuthRequest(
                email = signInState.value.email,
                password = signInState.value.password
            )

            val result = authUseCase.auth(authRequest)

            result.collect {
                when(it) {
                    is NetworkResponse.Error -> {
                        setLoading(false)
                        setErrorMassage(it.errorMessage)
                    }

                    is NetworkResponse.Success<*> -> {
                        setLoading(false)
                        signInState.value = signInState.value.copy(isSignIn = true)
                    }

                    is NetworkResponse.Loading -> {
                        setLoading(true)
                    }
                }
            }
        }
    }
}