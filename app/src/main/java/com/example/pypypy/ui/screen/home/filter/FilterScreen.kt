package com.example.pypypy.ui.screen.home.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pypypy.R
import com.example.pypypy.data.model.SnekersModel.PopularSneakersResponse
import com.example.pypypy.data.remote.NetworkResponseSneakers
import com.example.pypypy.data.remote.NetworkResponseUser
import com.example.pypypy.ui.screen.home.component.ProductItem
import com.example.pypypy.ui.screen.home.component.topPanelForSort
import com.example.pypypy.ui.screen.home.favourite.FavouriteScreenViewModel
import com.example.pypypy.ui.screen.home.popylar.PopylarSneakersViewModel
import com.example.pypypy.ui.screen.home.sort.LikeModel
import com.example.pypypy.ui.theme.MatuleTheme
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchScreen() {
    val viewModel = koinViewModel<PopylarSneakersViewModel>()
    val query by viewModel.query.collectAsState()
    val history by viewModel.history.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val favouriteViewModel = koinViewModel<FavouriteScreenViewModel>()
    val sneakersFavouriteState by favouriteViewModel.userState.collectAsState()
    val likesState = remember { viewModel.signInState }

    LaunchedEffect(Unit) {
        favouriteViewModel.getFavourite()
    }

    LaunchedEffect(sneakersFavouriteState) {
        (sneakersFavouriteState as? NetworkResponseUser.Success)?.data?.let { favItems ->
            likesState.forEach { like ->
                like.isLike = favItems.any { it.id == like.shoeId }
            }
        }
    }

    Scaffold(
        topBar = {
            topPanelForSort(
                title = "Поиск",
                leftImage = painterResource(R.drawable.direction_left),
                textSize = 24,
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            SearchInput(
                query = query,
                onQueryChange = viewModel::onQueryChanged
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (query.isEmpty()) {
                SearchHistory(
                    history = history,
                    onItemClick = { item ->
                        coroutineScope.launch {
                            viewModel.onItemClicked(item)
                        }
                    }
                )
            } else {
                when (searchResults) {
                    is NetworkResponseSneakers.Success -> {
                        val sneakers = (searchResults as NetworkResponseSneakers.Success<List<PopularSneakersResponse>>).data
                        if (sneakers.isEmpty()) {
                            EmptyResults()
                        } else {
                            SearchResultsGrid(
                                sneakers = sneakers,
                                viewModel = viewModel,
                                likesState = likesState
                            )
                        }
                    }
                    is NetworkResponseSneakers.Error -> {
                        ErrorMessage((searchResults as NetworkResponseSneakers.Error).errorMessage)
                    }
                    NetworkResponseSneakers.Loading -> {
                        LoadingIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun SearchResultsGrid(
    sneakers: List<PopularSneakersResponse>,
    viewModel: PopylarSneakersViewModel,
    likesState: MutableList<LikeModel>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(sneakers, key = { it.id }) { sneaker ->
            val isFavorite = likesState
                .find { it.shoeId == sneaker.id }
                ?.isLike ?: false
            val isInCart = viewModel.cartState
                .find { it.shoeId == sneaker.id }
                ?.inCart ?: false

            ProductItem(
                title = sneaker.category ?: "",
                name = sneaker.productName,
                price = "₽${sneaker.cost}",
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

@Composable
private fun SearchInput(
    query: String,
    onQueryChange: (String) -> Unit
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier.fillMaxWidth().padding(top=20.dp),
        placeholder = { Text("Поиск") },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.check),
                contentDescription = "Поиск",
                modifier = Modifier.size(24.dp)
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        painter = painterResource(R.drawable.vector),
                        contentDescription = "Очистить",
                        modifier = Modifier.size(24.dp)
                    )
                }
            } else {
                IconButton(onClick = { /* Голосовой поиск */ }) {
                    Icon(
                        painter = painterResource(R.drawable.vector),
                        contentDescription = "Голосовой поиск",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MatuleTheme.colors.block,
            unfocusedContainerColor = MatuleTheme.colors.block,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
private fun SearchHistory(
    history: List<String>,
    onItemClick: (String) -> Unit
) {
    Column {
        Text(
            text = "История поиска",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        if (history.isEmpty()) {
            Text(
                text = "История поиска пуста",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        } else {
            LazyColumn {
                items(history) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onItemClick(item) }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.group_325),
                            contentDescription = "История",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = item, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyResults() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Ничего не найдено", fontSize = 16.sp, color = Color.Gray)
    }
}

@Composable
fun ErrorMessage(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Ошибка: $message", fontSize = 16.sp, color = Color.Red)
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}