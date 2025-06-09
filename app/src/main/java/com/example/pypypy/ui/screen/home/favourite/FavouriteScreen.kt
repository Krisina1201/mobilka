package com.example.pypypy.ui.screen.home.favourite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pypypy.R
import com.example.pypypy.data.remote.NetworkResponseSneakers
import com.example.pypypy.data.remote.NetworkResponseUser
import com.example.pypypy.ui.screen.home.component.BottomBar
import com.example.pypypy.ui.screen.home.component.ProductItem
import com.example.pypypy.ui.screen.home.component.TopPanel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FavouriteScreen() {
    val favouriteViewModel = koinViewModel<FavouriteScreenViewModel>()

    Scaffold(
        topBar = { TopPanel(title = "Избранное", textSize = 16,
            leftImage = painterResource(R.drawable.direction_left),
            rightImage = painterResource(R.drawable.heart)
        ) },

        bottomBar = { BottomBar({}) }
    ) {
        paddingValues ->
        favouriteContent(paddingValues, viewModel = favouriteViewModel)
    }
}

@Composable
fun favouriteContent(paddingValues: PaddingValues, viewModel: FavouriteScreenViewModel){
    val sneakersState by viewModel.userState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getFavourite()
    }
    Column(
        modifier = Modifier.padding(paddingValues = paddingValues),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        when (val state = sneakersState) {
            is NetworkResponseUser.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResponseUser.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(21.dp),
                    verticalArrangement = Arrangement.spacedBy(21.dp),
                    horizontalArrangement = Arrangement.spacedBy(21.dp)
                ) {
                    items(state.data) { sneaker ->
                        ProductItem(
                            title = "Best Seller",
                            name = sneaker.productName,
                            price = "₽"+sneaker.count.toString(),
                            imageRes = painterResource(R.drawable.nadejda),
                            onClick = {},
                        )
                    }
                }
            }
            is NetworkResponseUser.Error -> {
                Text("Error: ${state.errorMessage}")
            }
        }
    }
}