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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pypypy.R
import com.example.pypypy.data.model.SnekersModel.PopularSneakersResponse
import com.example.pypypy.data.remote.NetworkResponseSneakers
import com.example.pypypy.data.remote.NetworkResponseUser
import com.example.pypypy.ui.screen.home.component.BottomBar
import com.example.pypypy.ui.screen.home.component.ProductItem
import com.example.pypypy.ui.screen.home.component.TopPanel
import com.example.pypypy.ui.screen.home.sort.LikeModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FavouriteScreen(onNavigationToHome: () -> Unit,
                    onNavigationToTrash: () -> Unit) {
    val favouriteViewModel = koinViewModel<FavouriteScreenViewModel>()

    Scaffold(
        topBar = { TopPanel(title = "Избранное", textSize = 16,
            leftImage = painterResource(R.drawable.direction_left),
            rightImage = painterResource(R.drawable.heart)
        ) },

        bottomBar = { BottomBar(
            favouriteClick = {},
            backgroundFavouriteImage = painterResource(R.drawable.heart_blue),
            backgroundHomeImage = painterResource(R.drawable.home),
            homeClick = onNavigationToHome,
            trashClick = onNavigationToTrash
        ) }
    ) {
            paddingValues ->
        favouriteContent(paddingValues, viewModel = favouriteViewModel)
    }
}

@Composable
fun favouriteContent(paddingValues: PaddingValues, viewModel: FavouriteScreenViewModel){
    val sneakersState by viewModel.userState.collectAsState()
    val favouriteViewModel = koinViewModel<FavouriteScreenViewModel>()
    val sneakersFavouriteState by favouriteViewModel.userState.collectAsState()
    val likesState = remember { viewModel.signInState }


    LaunchedEffect(Unit) {
        viewModel.getFavourite()
    }

    LaunchedEffect(sneakersFavouriteState) {
        (sneakersFavouriteState as? NetworkResponseUser.Success)?.data?.let { favItems ->
            val updatedLikes = likesState.map { like ->
                like.copy(isLike = favItems.any { it.id == like.shoeId })
            }.toMutableList()
            likesState.clear()
            likesState.addAll(updatedLikes)
        }
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
                LaunchedEffect(state.data) {
                    state.data.forEach { sneaker ->
                        if (likesState.none { it.shoeId == sneaker.id }) {
                            val isFav = (sneakersFavouriteState as? NetworkResponseUser.Success)
                                ?.data?.any { it.id == sneaker.id } ?: false
                            likesState.add(LikeModel(sneaker.id, isFav))
                        }
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(21.dp),
                    verticalArrangement = Arrangement.spacedBy(21.dp),
                    horizontalArrangement = Arrangement.spacedBy(21.dp)
                ) {
                    items(state.data) { sneaker ->
                        val isFavorite = likesState
                            .find { it.shoeId == sneaker.id }
                            ?.isLike ?: false
                        val isInCart = viewModel.cartState
                            .find { it.shoeId == sneaker.id }
                            ?.inCart ?: false
                        ProductItem(
                            title = "Best Seller",
                            name = sneaker.productName,
                            price = "₽"+sneaker.cost,
                            imageRes = painterResource(R.drawable.shoe2),
                            onClick = {},
                            likeImage = if (isFavorite) {
                                painterResource(R.drawable.icon)
                            } else {
                                painterResource(R.drawable.black_heart)
                            },
                            likeClick = {
                                val likeModel = likesState.find { it.shoeId == sneaker.id }
                                likeModel?.let {
                                    val newState = !it.isLike
                                    likesState[likesState.indexOf(it)] = it.copy(isLike = newState)

                                    if (newState) {
                                        viewModel.addToFavourite(sneaker.id)
                                    } else {
                                        viewModel.deleteFromFavourite(sneaker.id)
                                    }
                                }
                            },
                            cartImage = if (isInCart) {
                                painterResource(R.drawable.plus)
                            } else {
                                painterResource(R.drawable.korr)
                            },
                            cartClick = {
                                viewModel.addToCart(sneaker.id)
                            }
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