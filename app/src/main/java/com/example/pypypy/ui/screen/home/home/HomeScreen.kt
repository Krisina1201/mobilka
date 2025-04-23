package com.example.pypypy.ui.screen.home.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.pypypy.R
import com.example.pypypy.ui.screen.home.component.ProductItem

@Preview(showBackground = true)
@Composable
fun HomeScreen() {
    Column(Modifier.background(Color(0xF7F7F9))) {
        ProductItem(
            title = "Best Seller",
            name = "Nike Air Max",
            price = "₽752.00",
            imageRes = painterResource(R.drawable.nadejda),
            onClick = {}
        )
    }

}