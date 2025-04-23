package com.example.pypypy.ui.screen.signUp.forgotPassword

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ForgotPasswordViewModel: ViewModel() {
    var signInState = mutableStateOf(ForgotPasswordState())
        private set

    val emailHasError = derivedStateOf {
        if(signInState.value.email.isEmpty()) return@derivedStateOf false
        !android.util.Patterns.EMAIL_ADDRESS.matcher(signInState.value.email).matches()
    }

    fun setEmail(email: String){
        signInState.value = signInState.value.copy(email = email)
    }
}