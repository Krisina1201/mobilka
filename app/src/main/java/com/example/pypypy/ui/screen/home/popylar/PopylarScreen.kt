package com.example.pypypy.ui.screen.home.popylar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pypypy.R
import com.example.pypypy.data.model.SnekersModel.PopularSneakersResponse
import com.example.pypypy.data.remote.NetworkResponseSneakers
import com.example.pypypy.data.remote.NetworkResponseUser
import com.example.pypypy.ui.screen.home.component.ProductItem
import com.example.pypypy.ui.screen.home.component.TopPanel
import com.example.pypypy.ui.screen.home.component.checkSneakersInFavourite
import com.example.pypypy.ui.screen.home.favourite.FavouriteScreenViewModel
import com.example.pypypy.ui.screen.home.sort.LikeModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen() {
    val sneakersViewModel: PopylarSneakersViewModel = koinViewModel<PopylarSneakersViewModel>()

    Scaffold(
        topBar = {
            TopPanel(title = "Популярное",
                leftImage = painterResource(R.drawable.direction_left),
                rightImage = painterResource(R.drawable.icon),
                textSize = 16
            )
        }
    ) {
            paddingValues ->
        PopularScreenContent(paddingValues = paddingValues, sneakersViewModel
        );
    }
}

@Composable
fun PopularScreenContent(
    paddingValues: PaddingValues,
    viewModel: PopylarSneakersViewModel
) {
    val sneakersState by viewModel.sneakersState.collectAsState()
    val favouriteViewModel = koinViewModel<FavouriteScreenViewModel>()
    val sneakersFavouriteState by favouriteViewModel.userState.collectAsState()
    val likesState = remember { viewModel.signInState }

    // Загружаем данные при первом запуске
    LaunchedEffect(Unit) {
        viewModel.fetchSneakers()
        favouriteViewModel.getFavourite()
    }

    // Обновляем состояние лайков при изменении избранного
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
    ) {
        when (val state = sneakersState) {
            is NetworkResponseSneakers.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResponseSneakers.Success -> {
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

                        ProductItem(
                            title = "Best Seller",
                            name = sneaker.productName,
                            price = "₽${sneaker.count}",
                            imageRes = painterResource(R.drawable.nadejda),
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
                            }
                        )
                    }
                }
            }
            is NetworkResponseSneakers.Error -> {
                Text("Error: ${state.errorMessage}")
            }
        }
    }
}