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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pypypy.R
import com.example.pypypy.data.model.SnekersModel.PopularSneakersResponse
import com.example.pypypy.data.remote.NetworkResponseSneakers
import com.example.pypypy.ui.screen.home.component.ProductItem
import com.example.pypypy.ui.screen.home.component.topPanelForSort
import com.example.pypypy.ui.screen.home.popylar.PopylarSneakersViewModel
import com.example.pypypy.ui.theme.MatuleTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SortScreen(optionSort: String) {
    val sneakersViewModel: PopylarSneakersViewModel = koinViewModel<PopylarSneakersViewModel>()

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
            viewModel =  sneakersViewModel)
    }
}

@Composable
fun ContentCheck(paddingValues: PaddingValues, onCategorySelected: (String) -> Unit,
                 selectedCategory: String, viewModel: PopylarSneakersViewModel) {
    val sneakersState by viewModel.sneakersState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.allSneakers()
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
            optionSort = selectedCategory)
    }
}

@Composable
fun LazyRowSneakers(sneakersState:  NetworkResponseSneakers<List<PopularSneakersResponse>>,
                     optionSort: String){
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
                contentPadding = PaddingValues(end = 21.dp, start = 21.dp, bottom = 21.dp),
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