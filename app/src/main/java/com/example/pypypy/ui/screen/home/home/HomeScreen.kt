package com.example.pypypy.ui.screen.home.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pypypy.R
import com.example.pypypy.data.remote.NetworkResponseSneakers
import com.example.pypypy.ui.screen.home.component.ProductItem
import com.example.pypypy.ui.screen.signUp.signin.SignInViewModel
import org.koin.compose.viewmodel.koinViewModel

@Preview(showBackground = true)
@Composable
fun HomeScreen() {
    val sneakersViewModel: PopylarSneakersViewModel = koinViewModel<PopylarSneakersViewModel>()



    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(top = 35.dp, start = 16.dp, )
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.CenterStart)
                        .padding(end = 16.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.direction_left),
                        contentDescription = "Назад"
                    )
                }

                Text(text = "Популярное",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.Center)
                )

                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(44.dp)
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.icon),
                        contentDescription = "heart"
                    )
                }

            }
        }
    ) {
            paddingValues ->
        PopularScreenContent(paddingValues = paddingValues, sneakersViewModel);
    }
}

@Composable
fun PopularScreenContent(
    paddingValues: PaddingValues,
    viewModel: PopylarSneakersViewModel
) {
    val sneakersState by viewModel.sneakersState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.fetchSneakers()
    }
    Column(
        modifier = Modifier.padding(paddingValues = paddingValues),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        when (val state = sneakersState) {
            is NetworkResponseSneakers.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResponseSneakers.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // 2 столбца
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
            is NetworkResponseSneakers.Error -> {
                Text("Error: ${state.errorMessage}")
            }
        }
    }
}
