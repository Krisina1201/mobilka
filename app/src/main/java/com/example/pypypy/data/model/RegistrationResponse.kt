package com.example.pypypy.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationResponse(
    val token: String
)
