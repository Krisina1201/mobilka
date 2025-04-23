package com.example.pypypy.ui.screen.signUp.regist

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pypypy.data.model.RegistrationRequest
import com.example.pypypy.data.remote.NetworkResponse
import com.example.pypypy.domain.usecase.AuthUseCase
import kotlinx.coroutines.launch

class RegistrViewModel(val authUseCase: AuthUseCase): ViewModel() {
    var signInState = mutableStateOf(RegostrInState())
        private set

    val emailHasError = derivedStateOf {
        if(signInState.value.email.isEmpty()) return@derivedStateOf false
        !android.util.Patterns.EMAIL_ADDRESS.matcher(signInState.value.email).matches()
    }

    val nameHasError = derivedStateOf {
        if(signInState.value.email.isEmpty()) return@derivedStateOf false
        signInState.value.name.isBlank() || !signInState.value.name.matches(Regex("[a-zA-Zа-яА-Я]+"))
    }

    val passwordHasError = derivedStateOf {
        signInState.value.password.length < 8
    }

    fun setName(name: String) {
        signInState.value = signInState.value.copy(name = name)
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

    fun registration() {
        viewModelScope.launch {
            val registrationRequest = RegistrationRequest(
                email = signInState.value.email,
                password = signInState.value.password,
                userName = signInState.value.name
            )

            val result = authUseCase.registration(registrationRequest)

            result.collect {it ->
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