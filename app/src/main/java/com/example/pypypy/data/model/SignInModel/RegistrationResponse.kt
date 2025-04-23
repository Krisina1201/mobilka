package com.example.pypypy.data.model.SignInModel

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationResponse(
    val token: String
)
