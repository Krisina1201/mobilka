package com.example.pypypy.data.repository

import com.example.pypypy.data.remote.retrofit.AuthSource
import com.example.pypypy.data.local.DataStore
import com.example.pypypy.data.model.SignInModel.AuthRequest
import com.example.pypypy.data.model.SignInModel.RegistrationRequest
import com.example.pypypy.data.model.SignInModel.RegistrationResponse
import com.example.pypypy.data.model.SignInModel.UserResponce
import com.example.pypypy.data.model.SnekersModel.PopularSneakersResponse
import com.example.pypypy.data.remote.NetworkResponse
import com.example.pypypy.data.remote.NetworkResponseSneakers
import com.example.pypypy.data.remote.NetworkResponseUser
import com.example.pypypy.data.remote.RetrofitClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow


class AuthRepositoryImpl(val dataStore: DataStore, val authRemoteSource: AuthSource) {

    suspend fun registration(registrationRequest: RegistrationRequest){
        val result = authRemoteSource.registration(registrationRequest)
        dataStore.setUserId(result.userId)
        //dataStore.setToken(result.token)
    }

    suspend fun auth(authRequest: AuthRequest): UserResponce {
        val result = authRemoteSource.login(authRequest)
        dataStore.setUserId(result.userId)
        //dataStore.setToken(result.token)

        return result
    }

    suspend fun postFav(userId: Int, shoeId: Int): Boolean {
        val result = authRemoteSource.addFavorite(userId = userId, shoes_id = shoeId)
        return result
    }

    suspend fun deleteFav(userId: Int, shoeId: Int): Boolean {
        val result = authRemoteSource.deleteFavorite(userId = userId, shoes_id = shoeId)
        return result
    }

    fun popylarSneakers(): Flow<NetworkResponseSneakers<List<PopularSneakersResponse>>> = flow {

        try {
            emit(NetworkResponseSneakers.Loading)
            val result = authRemoteSource.popular()
            emit(NetworkResponseSneakers.Success(result))
        } catch (e: Exception) {
            emit(NetworkResponseSneakers.Error(e.message ?: "Unknown Error"))
        }
    }

    suspend fun getProfile(userId: Int): Flow<NetworkResponseUser<List<PopularSneakersResponse>>> = flow {
        try {
            emit(NetworkResponseUser.Loading)
            val token = dataStore.tokenFlow.first()

            if (token.isEmpty()) {
                emit(NetworkResponseUser.Error("User is not authenticated"))
                return@flow
            }

            val result = authRemoteSource.getProfile(userId)
            emit(NetworkResponseUser.Success(result))
        } catch (e: Exception) {
            emit(NetworkResponseUser.Error(e.message ?: "Unknown error occurred"))
        }
    }

    fun getSneakers(): Flow<NetworkResponseSneakers<List<PopularSneakersResponse>>> = flow {

        try {
            emit(NetworkResponseSneakers.Loading)
            val result = authRemoteSource.allSneakers()
            emit(NetworkResponseSneakers.Success(result))
        } catch (e: Exception) {
            emit(NetworkResponseSneakers.Error(e.message ?: "Unknown Error"))
        }
    }
}

//class AuthRepository(private val api: AuthSource) {
//    suspend fun registration(registrationRequest: RegistrationRequest): RegistrationResponse {
//        delay(3000)
//        return api.registration(registrationRequest)
//    }
//}