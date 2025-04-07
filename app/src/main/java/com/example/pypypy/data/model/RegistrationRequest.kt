package com.example.pypypy.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequest(
    val userName: String,
    val email: String,
    val password: String
)
