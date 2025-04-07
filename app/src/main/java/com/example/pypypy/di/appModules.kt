package com.example.pypypy.di

import androidx.datastore.dataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pypypy.data.local.DataStore
import com.example.pypypy.data.remote.RetrofitClient
import com.example.pypypy.data.remote.retrofit.AuthSource
import com.example.pypypy.data.repository.AuthRepository
import com.example.pypypy.data.repository.AuthRepositoryImpl
import com.example.pypypy.domain.usecase.AuthUseCase
import com.example.pypypy.ui.screen.regist.RegistrViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    single { DataStore(get()) }
    single<AuthSource> { RetrofitClient.auth }
    single<AuthRepository> { get() }
    single { AuthUseCase(get(), get()) }
    viewModel { RegistrViewModel(get()) }
}