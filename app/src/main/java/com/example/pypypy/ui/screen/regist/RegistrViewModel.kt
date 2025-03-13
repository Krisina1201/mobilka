package com.example.pypypy.ui.screen.regist

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RegistrViewModel: ViewModel() {
    var signInState = mutableStateOf(RegostrInState())
        private set

    val emailHasError = derivedStateOf {
        if(signInState.value.email.isEmpty()) return@derivedStateOf false
        !android.util.Patterns.EMAIL_ADDRESS.matcher(signInState.value.email).matches()
    }

    val nameHasError = derivedStateOf {
        if(signInState.value.name.isEmpty()) return@derivedStateOf false
        !android.util.Patterns.EMAIL_ADDRESS.matcher(signInState.value.name).matches()
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
}