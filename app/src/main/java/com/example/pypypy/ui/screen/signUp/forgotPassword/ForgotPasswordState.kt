package com.example.pypypy.ui.screen.signUp.forgotPassword

data class ForgotPasswordState (
    var email: String = "",
    var isVisiblePassword: Boolean = false,
    var isLoading: Boolean = false,
    var isSignIn: Boolean = false,
    var errorMessage: String? = null,
    var isApproval: Boolean = false
)