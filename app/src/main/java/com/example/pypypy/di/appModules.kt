package com.example.pypypy.di

import com.example.pypypy.data.local.DataStore
import com.example.pypypy.data.remote.RetrofitClient
import com.example.pypypy.data.remote.retrofit.AuthSource
import com.example.pypypy.data.repository.AuthRepositoryImpl
import com.example.pypypy.domain.usecase.AuthUseCase
import com.example.pypypy.ui.screen.home.component.CartManager
import com.example.pypypy.ui.screen.home.favourite.FavouriteScreenViewModel
import com.example.pypypy.ui.screen.home.garbage.GarbageScreenViewModel
import com.example.pypypy.ui.screen.home.popylar.PopylarSneakersViewModel
import com.example.pypypy.ui.screen.home.sort.SortScreenViewModel
import com.example.pypypy.ui.screen.signUp.regist.RegistrViewModel
import com.example.pypypy.ui.screen.signUp.signin.SignInViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    single { DataStore(get()) }
    single<AuthSource> { RetrofitClient.auth }

    single<AuthRepositoryImpl> {
        AuthRepositoryImpl(
            dataStore = get(),
            authRemoteSource = get()
        )
    }

    single {
        AuthUseCase(
            dataStore = get(),
            authRepository = get<AuthRepositoryImpl>()
        )
    }
    viewModel { CartManager(get()) }

    viewModel { RegistrViewModel(get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { PopylarSneakersViewModel(get(),get())}
    viewModel { FavouriteScreenViewModel(get(),get(),) }
    viewModel { SortScreenViewModel(get(),get(),) }
    viewModel { GarbageScreenViewModel(get()) }
}