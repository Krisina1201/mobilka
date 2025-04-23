package com.example.pypypy.data.model.SignInModel

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    var email: String,
    var password:String
)
