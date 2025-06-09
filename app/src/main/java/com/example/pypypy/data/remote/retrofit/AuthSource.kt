package com.example.pypypy.data.remote.retrofit

import com.example.pypypy.data.model.SignInModel.AuthRequest
import com.example.pypypy.data.model.SignInModel.RegistrationRequest
import com.example.pypypy.data.model.SignInModel.RegistrationResponse
import com.example.pypypy.data.model.SignInModel.UserResponce
import com.example.pypypy.data.model.SnekersModel.PopularSneakersResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthSource {
    @POST("/registration")
    suspend fun registration(@Body registrationRequest: RegistrationRequest): UserResponce
    @POST("/login")
    suspend fun login(@Body authRequest: AuthRequest): UserResponce
    @GET("/popular")
    suspend fun popular(): List<PopularSneakersResponse>
    @GET("/allSneakers")
    suspend fun allSneakers(): List<PopularSneakersResponse>
    @GET("/favourites/{id}")
    suspend fun getProfile(
        @Path("id") userId: Int
    ): List<PopularSneakersResponse>
}