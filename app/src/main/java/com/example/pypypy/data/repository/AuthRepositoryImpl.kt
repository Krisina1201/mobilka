package com.example.pypypy.data.repository

import com.example.pypypy.data.remote.retrofit.AuthSource
import com.example.pypypy.data.local.DataStore
import com.example.pypypy.data.model.AuthRequest
import com.example.pypypy.data.model.RegistrationRequest
import com.example.pypypy.data.model.RegistrationResponse
import kotlinx.coroutines.delay


class AuthRepositoryImpl(val dataStore: DataStore, val authRemoteSource: AuthSource) {

    suspend fun registration(registrationRequest: RegistrationRequest){
        val result = authRemoteSource.registration(registrationRequest)
        dataStore.setToken(result.token)
    }

    suspend fun auth(authRequest: AuthRequest) {
        val result = authRemoteSource.login(authRequest)
        dataStore.setToken(result.token)
    }
}

class AuthRepository(private val api: AuthSource) {
    suspend fun registration(registrationRequest: RegistrationRequest): RegistrationResponse {
        delay(3000)
        return api.registration(registrationRequest)
    }
}