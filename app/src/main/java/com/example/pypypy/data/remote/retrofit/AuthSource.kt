package com.example.pypypy.data.remote.retrofit

import com.example.pypypy.data.model.AuthRequest
import com.example.pypypy.data.model.RegistrationRequest
import com.example.pypypy.data.model.RegistrationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthSource {
    @POST("/registration")
    suspend fun registration(@Body registrationRequest: RegistrationRequest): RegistrationResponse
    @POST("/login")
    suspend fun login(@Body authRequest: AuthRequest): RegistrationResponse
}