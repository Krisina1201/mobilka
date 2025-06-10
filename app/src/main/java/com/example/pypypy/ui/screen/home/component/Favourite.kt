package com.example.pypypy.ui.screen.home.component

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.pypypy.R
import com.example.pypypy.data.model.SnekersModel.PopularSneakersResponse
import com.example.pypypy.data.remote.NetworkResponseUser

@Composable
fun checkSneakersInFavourite(sneakersId: Int, favList: NetworkResponseUser<List<PopularSneakersResponse>>) : Boolean {
    when (val state = favList) {
        is NetworkResponseUser.Loading -> {
            CircularProgressIndicator()
        }
        is NetworkResponseUser.Error -> {
            Text("Error: ${state.errorMessage}")
        }

        is NetworkResponseUser.Success -> {
            for (i in state.data) {
                if (i.id == sneakersId) {
                    return true
                }
            }
            return false
        }
    }
    return false
}
