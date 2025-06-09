package com.example.pypypy.data.remote

sealed class NetworkResponse{
    data class Success<T>(val data: T): NetworkResponse()
    data object Loading: NetworkResponse()
    data class Error(val errorMessage: String): NetworkResponse()
}

sealed class NetworkResponseUser <out T>{
    data class Success<T>(val data: T): NetworkResponseUser<T>()
    data object Loading: NetworkResponseUser<Nothing>()
    data class Error(val errorMessage: String): NetworkResponseUser<Nothing>()
}

