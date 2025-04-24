package com.example.pypypy.domain.usecase

import com.example.pypypy.data.local.DataStore
import com.example.pypypy.data.model.SignInModel.AuthRequest
import com.example.pypypy.data.model.SignInModel.RegistrationRequest
import com.example.pypypy.data.model.SnekersModel.PopularSneakersResponse
import com.example.pypypy.data.remote.NetworkResponse
import com.example.pypypy.data.remote.NetworkResponseSneakers
import com.example.pypypy.data.repository.AuthRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthUseCase(private val dataStore: DataStore,
    private val authRepository: AuthRepositoryImpl)  {
    val token: Flow<String> = dataStore.tokenFlow

    suspend fun registration(registrationRequest: RegistrationRequest): Flow<NetworkResponse> = flow {
        try {
            emit(NetworkResponse.Loading)
            val result = authRepository.registration(registrationRequest)
            dataStore.setToken(result.toString())
            emit(NetworkResponse.Success(result))
        } catch (e: Exception) {
            e.message?.let {
                emit(NetworkResponse.Error(it))
                return@flow
            }
            emit(NetworkResponse.Error("Unknown Error"))
        }
    }

    suspend fun auth(authRequest: AuthRequest) :Flow<NetworkResponse> = flow {
        try {
            emit(NetworkResponse.Loading)
            val result = authRepository.auth(authRequest)
            dataStore.setToken(result.toString())
            emit(NetworkResponse.Success(result))
        } catch (e: Exception) {
            e.message?.let {
                emit(NetworkResponse.Error(it))
                return@flow
            }
            emit(NetworkResponse.Error("Unknown Error"))
        }

    }

    suspend fun getSneakers(): Flow<NetworkResponseSneakers<List<PopularSneakersResponse>>> {
        return authRepository.getSneakers()
    }
}