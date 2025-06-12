package com.example.pypypy.data.remote.retrofit

import com.example.pypypy.data.model.SignInModel.AuthRequest
import com.example.pypypy.data.model.SignInModel.RegistrationRequest
import com.example.pypypy.data.model.SignInModel.RegistrationResponse
import com.example.pypypy.data.model.SignInModel.UserResponce
import com.example.pypypy.data.model.SnekersModel.Basket
import com.example.pypypy.data.model.SnekersModel.PopularSneakersResponse
import com.example.pypypy.data.model.SnekersModel.SneakersInBasket
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
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
    @POST("/users/{user_id}/favorites/shoes/{shoes_id}")
    suspend fun addFavorite(
        @Path(value = "user_id") userId: Int,
        @Path(value = "shoes_id") shoes_id: Int
    ): Boolean

    @DELETE("/users/{user_id}/favorites/shoes/{shoes_id}")
    suspend fun deleteFavorite(
        @Path(value = "user_id") userId: Int,
        @Path(value = "shoes_id") shoes_id: Int
    ): Boolean

    @GET("/basket/{userId}")
    suspend fun getBasket(
        @Path("userId") userId: Int
    ): List<SneakersInBasket>

    @POST("/basket")
    suspend fun addInBasket (
        @Body basket: Basket
    ): Boolean

    @HTTP(method = "DELETE", path = "/basket", hasBody = true)
    suspend fun deleteInBasket(@Body basket: Basket): Boolean
}