package com.example.pypypy.ui.screen.home.sort

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
import com.example.pypypy.R
import com.example.pypypy.data.remote.NetworkResponseSneakers
import com.example.pypypy.ui.screen.home.component.ProductItem
import com.example.pypypy.ui.screen.home.component.topPanelForSort
import com.example.pypypy.ui.screen.home.popylar.PopylarSneakersViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SortScreen(optionSort: String) {
    val sneakersViewModel: PopylarSneakersViewModel = koinViewModel<PopylarSneakersViewModel>()

    Scaffold(
        topBar = { topPanelForSort(
            title = "Outdoor",
            leftImage = painterResource(R.drawable.direction_left),
            textSize = 16
        ) }
    ) {
        paddingValues ->
        ContentCheck(paddingValues, optionSort, sneakersViewModel)
    }
}

@Composable
fun ContentCheck(paddingValues: PaddingValues, optionSort: String, viewModel: PopylarSneakersViewModel) {
    val sneakersState by viewModel.sneakersState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.fetchSneakers()
    }
    Column(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        when (val state = sneakersState) {
            is NetworkResponseSneakers.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResponseSneakers.Success -> {
                var filteredSneakers = state.data.filter { it.category == optionSort }

                if (optionSort == "Всё") {
                    filteredSneakers = state.data
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(21.dp),
                    verticalArrangement = Arrangement.spacedBy(21.dp),
                    horizontalArrangement = Arrangement.spacedBy(21.dp)
                ) {
                    items(filteredSneakers) { sneaker ->
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
            is NetworkResponseSneakers.Error -> {
                Text("Error: ${state.errorMessage}")
            }
        }
    }
}