package com.example.pypypy.ui.screen.home.garbage

import AuthButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pypypy.R
import com.example.pypypy.data.remote.NetworkResponseSneakers
import com.example.pypypy.ui.screen.hello.component.text
import com.example.pypypy.ui.screen.home.component.CartManager
import com.example.pypypy.ui.screen.home.component.SwipeableProductItem
import com.example.pypypy.ui.screen.home.component.TopPanel
import com.example.pypypy.ui.screen.home.component.topPanelForSort
import com.example.pypypy.ui.screen.home.favourite.FavouriteScreenViewModel
import com.example.pypypy.ui.screen.home.popylar.PopylarSneakersViewModel
import com.example.pypypy.ui.screen.home.sort.SortScreenViewModel
import com.example.pypypy.ui.theme.MatuleTheme
import io.ktor.websocket.Frame
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.context.KoinContext


@Composable
fun GarbageScreen() {
    val context = LocalContext.current
    val viewModel = koinViewModel<GarbageScreenViewModel>()
    val cartManager = koinViewModel<CartManager>()
    LaunchedEffect(Unit) {
        cartManager.cartUpdates.collect {
            viewModel.refreshBasket()
        }
    }
    Scaffold(
        topBar = {
            topPanelForSort(
                title = "Корзина",
                leftImage = painterResource(R.drawable.direction_left),
                textSize = 16
            )
        }
    ) { paddingValues ->
        GarbageScreenContent(paddingValues, viewModel)
    }
}

@Composable
fun GarbageScreenContent(
    paddingValues: PaddingValues,
    viewModel: GarbageScreenViewModel
) {
    val sneakersState by viewModel.sneakersState.collectAsState()
    val totalPrice by viewModel.totalPrice.collectAsState()
    val deliveryPrice by viewModel.deliveryPrice.collectAsState()
    val finalPrice by viewModel.finalPrice.collectAsState()

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .padding(bottom = 40.dp)
    ) {
        when (val state = sneakersState) {
            is NetworkResponseSneakers.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is NetworkResponseSneakers.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = state.errorMessage, color = Color.Red)
                }
            }
            is NetworkResponseSneakers.Success -> {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(state.data) { item ->
                        SwipeableProductItem(
                            sneakers = item.sneakers,
                            quantity = item.countInBasket,
                            onClick = {  },
                            onIncrease = {
                                viewModel.updateQuantity(item.sneakers.id)
                            },
                            onDecrease = {
                                viewModel.minusQuantity(item.sneakers.id)
                            },
                            onRemove = {
                                viewModel.deleteFromBasket(shoeId = item.sneakers.id, count =  item.countInBasket)
                            }
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    Row(
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    ) {
                        Text("Сумма", modifier = Modifier.weight(1f), color = Color.Gray)
                        Text("₽$totalPrice")
                    }
                    Row(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Доставка", modifier = Modifier.weight(1f), color = Color.Gray)
                        Text("₽$deliveryPrice")
                    }
                    Row(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Итого", modifier = Modifier.weight(1f), color = MatuleTheme.colors.accent)
                        Text("₽$finalPrice", fontWeight = FontWeight.Bold)
                    }

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(50.dp),
                        shape = RoundedCornerShape(30.dp),
                        colors = buttonColors(
                            containerColor = Color(0xFF48B2E7),
                            contentColor = Color.White
                        ),
                        onClick = { /* Оформление заказа */ }
                    ) {
                        Text("Оформить заказ", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}