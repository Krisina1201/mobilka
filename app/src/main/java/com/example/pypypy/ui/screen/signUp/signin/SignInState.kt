package com.example.pypypy.ui.screen.signUp.signin

data class SignInState (
    var email: String = "123@mail.ru",
    var password: String = "12345",
    var isVisiblePassword: Boolean = false,
    var isLoading: Boolean = false,
    var isSignIn: Boolean = false,
    var errorMessage: String? = null
)