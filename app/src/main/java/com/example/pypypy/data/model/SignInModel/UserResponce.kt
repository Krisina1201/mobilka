package com.example.pypypy.data.model.SignInModel

import androidx.compose.runtime.Composable
import com.example.pypypy.data.model.SnekersModel.PopularSneakersResponse
import kotlinx.serialization.Serializable

@Serializable
data class UserResponce(
    val userId: Int,
    val userName: String,
    val email: String,
    val password: String,
    val favourites: MutableList<PopularSneakersResponse>,
    //val basket: MutableList<PopularSneakersResponse>
)
