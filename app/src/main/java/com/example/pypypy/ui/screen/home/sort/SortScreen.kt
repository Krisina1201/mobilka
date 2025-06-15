package com.example.pypypy.ui.screen.home.sort

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pypypy.R
import com.example.pypypy.data.model.SnekersModel.PopularSneakersResponse
import com.example.pypypy.data.remote.NetworkResponseSneakers
import com.example.pypypy.data.remote.NetworkResponseUser
import com.example.pypypy.ui.screen.home.component.ProductItem
import com.example.pypypy.ui.screen.home.component.topPanelForSort
import com.example.pypypy.ui.screen.home.favourite.FavouriteScreenViewModel
import com.example.pypypy.ui.screen.home.popylar.PopylarSneakersViewModel
import com.example.pypypy.ui.theme.MatuleTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SortScreen(optionSort: String) {
    val sneakersViewModel: PopylarSneakersViewModel = koinViewModel<PopylarSneakersViewModel>()
    val viewModel: SortScreenViewModel = koinViewModel<SortScreenViewModel>()

    var selectedCategory by remember { mutableStateOf(optionSort) }

    Scaffold(
        topBar = { topPanelForSort(
            title = selectedCategory,
            leftImage = painterResource(R.drawable.direction_left),
            textSize = 16,

        ) }
    ) {
        paddingValues ->
        ContentCheck(
            paddingValues = paddingValues,
            selectedCategory = selectedCategory,
            onCategorySelected = { category -> selectedCategory = category},
            viewModelpop =  sneakersViewModel, viewModel = viewModel)
    }
}

@Composable
fun ContentCheck(paddingValues: PaddingValues, onCategorySelected: (String) -> Unit,
                 selectedCategory: String, viewModelpop: PopylarSneakersViewModel, viewModel: SortScreenViewModel) {
    val sneakersState by viewModelpop.sneakersState.collectAsState()

    LaunchedEffect(Unit) {
        viewModelpop.allSneakers()
    }
    Column(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val langs = listOf("Всё", "Outdoor", "Tennis", "Podcrdyli")


        LazyRow (
            Modifier.padding(21.dp)
        ) {
            items(langs) { lang ->

                Button(
                    onClick = {
                        onCategorySelected(lang)
                    },
                    modifier = Modifier
                        .size(width = 108.dp, height = 40.dp)
                        .padding(end = 16.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = if (lang == selectedCategory) {
                            MatuleTheme.colors.accent
                        } else {
                        Color.White
                    }
                    )
                ) {
                    Text(text = lang, fontWeight = FontWeight.Normal, fontSize = 12.sp)
                }
            }
        }
        LazyRowSneakers(sneakersState = sneakersState,
            optionSort = selectedCategory, viewModel)
    }
}

@Composable
fun LazyRowSneakers(sneakersState:  NetworkResponseSneakers<List<PopularSneakersResponse>>,
                     optionSort: String,
                    viewModel: SortScreenViewModel){

    val favouriteViewModel = koinViewModel<FavouriteScreenViewModel>()
    val sneakersFavouriteState by favouriteViewModel.userState.collectAsState()

    LaunchedEffect(Unit) {
        favouriteViewModel.getFavourite()
    }

    val likesState = remember { viewModel.signInState }

    LaunchedEffect(sneakersFavouriteState, optionSort) {
        (sneakersFavouriteState as? NetworkResponseUser.Success)?.data?.let { favItems ->
            likesState.forEach { like ->
                like.isLike = favItems.any { it.id == like.shoeId }
            }
        }
    }

    when (val state = sneakersState) {
        is NetworkResponseSneakers.Loading -> {
            CircularProgressIndicator()
        }
        is NetworkResponseSneakers.Success -> {
            var filteredSneakerSt = state.data.filter { it.category == optionSort }
            if (optionSort == "Всё") {
                filteredSneakerSt = state.data
            }

            var filteredSneakers = filteredSneakerSt.also { list ->
                    list.forEach { sneaker ->
                        if (likesState.none { it.shoeId == sneaker.id }) {
                            val isFav = (sneakersFavouriteState as? NetworkResponseUser.Success)
                                ?.data?.any { it.id == sneaker.id } ?: false
                            likesState.add(LikeModel(sneaker.id, isFav))
                        }
                    }
                }


            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(end = 21.dp, start = 21.dp, bottom = 21.dp),
                verticalArrangement = Arrangement.spacedBy(21.dp),
                horizontalArrangement = Arrangement.spacedBy(21.dp)
            ) {
                items(filteredSneakers) { sneaker ->

                    val likeModel = likesState.find { it.shoeId == sneaker.id }
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
                        imageRes = painterResource(R.drawable.nadejda),
                        onClick = {},
                        likeImage = if (isFavorite) {
                            painterResource(R.drawable.icon)
                        } else {
                            painterResource(R.drawable.black_heart)
                        },
                        likeClick = {
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
                            painterResource(R.drawable.group_1072)
                        } else {
                            painterResource(R.drawable.group_1000000808)
                        },
                        cartClick = {
                            viewModel.addToCart(sneaker.id)
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
