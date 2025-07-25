package com.example.pypypy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pypypy.data.remote.RetrofitClient
import com.example.pypypy.data.local.DataStore
import com.example.pypypy.data.repository.AuthRepositoryImpl
import com.example.pypypy.domain.usecase.AuthUseCase
import com.example.pypypy.ui.screen.home.favourite.FavouriteScreen
import com.example.pypypy.ui.screen.home.garbage.GarbageScreen
import com.example.pypypy.ui.screen.home.home.HomeScreenHast
import com.example.pypypy.ui.screen.home.popylar.HomeScreen
import com.example.pypypy.ui.screen.home.search.SearchScreen
import com.example.pypypy.ui.screen.home.sort.SortScreen
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
                    composable("homeScreen") {
                        HomeScreenHast(
                            onNavigationToPopylarScreen = {navController.navigate("popylarScreen")},
                            onNavigationToSortScreen = { category ->
                                navController.navigate("sortScreen/$category")},
                            onNavigationToFavouriteScreen = {navController.navigate("favoriteScreen")},
                            onNavigationToTrashScreen = {navController.navigate("trashScreen")},
                            onNavigationToFilterScreen = {navController.navigate("filterScreen")}
                            )
                    }
                    composable("forgotPassword") { ForgotPass() }
                    composable("filterScreen") { SearchScreen() }
                    composable ("trashScreen") { GarbageScreen() }
                    composable("favoriteScreen") { FavouriteScreen(
                        onNavigationToHome = {navController.navigate("homeScreen")},
                        onNavigationToTrash = {navController.navigate("trashScreen")}
                    ) }
                    composable(
                        route = "sortScreen/{category}",
                        arguments = listOf(navArgument("category") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val category = backStackEntry.arguments?.getString("category") ?: ""
                        SortScreen(optionSort = category)
                    }
                    composable("popylarScreen") { HomeScreen() }
                    composable("registScreen") {
                        RegistorScreen(
                            onNavigationToSignScreen = { navController.navigate("signIn")},
                            onNavigationToHomeScreen = {navController.navigate("homeScreen")}
                        )
                    }
                }
            }
        }
    }

}

