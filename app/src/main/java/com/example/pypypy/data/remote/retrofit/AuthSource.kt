package com.example.pypypy.data.remote.retrofit

import com.example.pypypy.data.model.SignInModel.AuthRequest
import com.example.pypypy.data.model.SignInModel.RegistrationRequest
import com.example.pypypy.data.model.SignInModel.RegistrationResponse
import com.example.pypypy.data.model.SnekersModel.PopularSneakersResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthSource {
    @POST("/registration")
    suspend fun registration(@Body registrationRequest: RegistrationRequest): RegistrationResponse
    @POST("/login")
    suspend fun login(@Body authRequest: AuthRequest): RegistrationResponse
    @GET("/allSneakers")
    suspend fun popular(): List<PopularSneakersResponse>

}