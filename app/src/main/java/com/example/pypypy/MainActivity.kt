package com.example.pypypy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pypypy.data.remote.RetrofitClient
import com.example.pypypy.data.local.DataStore
import com.example.pypypy.data.repository.AuthRepositoryImpl
import com.example.pypypy.domain.usecase.AuthUseCase
import com.example.pypypy.ui.screen.home.home.HomeScreenHast
import com.example.pypypy.ui.screen.home.popylar.HomeScreen
import com.example.pypypy.ui.screen.signUp.forgotPassword.ForgotPass
import com.example.pypypy.ui.screen.signUp.regist.RegistorScreen
import com.example.pypypy.ui.screen.signUp.signin.SignInScreen
import com.example.pypypy.ui.theme.MatuleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val dataStore = DataStore(applicationContext)
        val repository = AuthRepositoryImpl(dataStore, RetrofitClient.auth)
        val authUseCase = AuthUseCase(dataStore, repository)

        setContent {
            MatuleTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "signIn") {
                    composable("signIn") {
                        SignInScreen(
                            onNavigationToScreenPassword = { navController.navigate("forgotPassword") },
                            onNavigationToScreenRegistor = { navController.navigate("registScreen") },
                            onNavigationToHome = {navController.navigate("homeScreen")}
                        )
                    }
                    composable("homeScreen") { HomeScreenHast() }
                    composable("forgotPassword") { ForgotPass() }
                    composable("registScreen") {
                        RegistorScreen(
                            onNavigationToSignScreen = { navController.navigate("signIn") }
                        )
                    }
                }
            }
        }
    }

}

