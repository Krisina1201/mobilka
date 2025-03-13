package com.example.pypypy.ui.screen.regist

data class RegostrInState (
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var isVisiblePassword: Boolean = false,
    var isLoading: Boolean = false,
    var isSignIn: Boolean = false,
    var errorMessage: String? = null,
    var isApproval: Boolean = false
)