package com.example.pypypy.ui.screen.regist

data class RegostrInState (
    var name: String = "ajdoeij",
    var email: String = "123@123.ru",
    var password: String = "123",
    var isVisiblePassword: Boolean = false,
    var isLoading: Boolean = false,
    var isSignIn: Boolean = false,
    var errorMessage: String? = null,
    var isApproval: Boolean = false
)