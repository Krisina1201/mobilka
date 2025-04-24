package com.example.pypypy.data.repository

import com.example.pypypy.data.remote.retrofit.AuthSource
import com.example.pypypy.data.local.DataStore
import com.example.pypypy.data.model.SignInModel.AuthRequest
import com.example.pypypy.data.model.SignInModel.RegistrationRequest
import com.example.pypypy.data.model.SignInModel.RegistrationResponse
import com.example.pypypy.data.model.SnekersModel.PopularSneakersResponse
import com.example.pypypy.data.remote.NetworkResponse
import com.example.pypypy.data.remote.NetworkResponseSneakers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class AuthRepositoryImpl(val dataStore: DataStore, val authRemoteSource: AuthSource) {

    suspend fun registration(registrationRequest: RegistrationRequest){
        val result = authRemoteSource.registration(registrationRequest)
        dataStore.setToken(result.token)
    }

    suspend fun auth(authRequest: AuthRequest) {
        val result = authRemoteSource.login(authRequest)
        dataStore.setToken(result.token)
    }

    suspend fun getSneakers(): Flow<NetworkResponseSneakers<List<PopularSneakersResponse>>> = flow {

        try {
            emit(NetworkResponseSneakers.Loading)
            val result = authRemoteSource.popular()
            emit(NetworkResponseSneakers.Success(result))
        } catch (e: Exception) {
            emit(NetworkResponseSneakers.Error(e.message ?: "Unknown Error"))
        }
    }
}

class AuthRepository(private val api: AuthSource) {
    suspend fun registration(registrationRequest: RegistrationRequest): RegistrationResponse {
        delay(3000)
        return api.registration(registrationRequest)
    }
}