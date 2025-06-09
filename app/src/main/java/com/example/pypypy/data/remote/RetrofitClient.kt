package com.example.pypypy.data.remote

import com.example.pypypy.data.remote.retrofit.AuthSource
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


object RetrofitClient {
    private const val URL = "http://26.85.192.162:8080"
    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(Json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
        .build()

    val auth by lazy {
        retrofit.create(AuthSource::class.java)
    }

    val protectedApi by lazy {
        retrofit.create(AuthSource::class.java)
    }
}