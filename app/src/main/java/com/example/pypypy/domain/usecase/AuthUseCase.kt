package com.example.pypypy.domain.usecase

import com.example.pypypy.data.local.DataStore
import com.example.pypypy.data.model.SignInModel.AuthRequest
import com.example.pypypy.data.model.SignInModel.RegistrationRequest
import com.example.pypypy.data.model.SignInModel.UserResponce
import com.example.pypypy.data.model.SnekersModel.PopularSneakersResponse
import com.example.pypypy.data.remote.NetworkResponse
import com.example.pypypy.data.remote.NetworkResponseSneakers
import com.example.pypypy.data.remote.NetworkResponseUser
import com.example.pypypy.data.repository.AuthRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class AuthUseCase(private val dataStore: DataStore,
    private val authRepository: AuthRepositoryImpl)  {
    val userIdGl: Flow<Int> = dataStore.userFlow

    suspend fun registration(registrationRequest: RegistrationRequest): Flow<NetworkResponse> = flow {
        try {
            emit(NetworkResponse.Loading)
            val result = authRepository.registration(registrationRequest)
            dataStore.setUserId(result.toString().toInt())
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
            emit(NetworkResponse.Success(result))
        } catch (e: Exception) {
            e.message?.let {
                emit(NetworkResponse.Error(it))
                return@flow
            }
            emit(NetworkResponse.Error("Unknown Error"))
        }

    }

    suspend fun postFav(shoeId: Int): Flow<NetworkResponse> = flow {
        try {
            emit(NetworkResponse.Loading)
            val result = authRepository.postFav(userIdGl.first(), shoeId)
            emit(NetworkResponse.Success(result))
        } catch (e: Exception) {
            e.message?.let {
                emit(NetworkResponse.Error(it))
                return@flow
            }
            emit(NetworkResponse.Error("Unknown Error"))
        }
    }

    fun deleteFav(shoeId: Int): Flow<NetworkResponse> = flow {
        try {
            emit(NetworkResponse.Loading)
            val result = authRepository.deleteFav(userIdGl.first(), shoeId)
            emit(NetworkResponse.Success(result))
        } catch (e: Exception) {
            e.message?.let {
                emit(NetworkResponse.Error(it))
                return@flow
            }
            emit(NetworkResponse.Error("Unknown Error"))
        }
    }

    suspend fun getUser(): Flow<NetworkResponseUser<List<PopularSneakersResponse>>> {
        return authRepository.getProfile(userIdGl.first())
    }

    fun getSneakers(): Flow<NetworkResponseSneakers<List<PopularSneakersResponse>>> {
        return authRepository.getSneakers()
    }

    fun popylar(): Flow<NetworkResponseSneakers<List<PopularSneakersResponse>>> {
        return authRepository.popylarSneakers()
    }
}