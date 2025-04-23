package com.example.pypypy.ui.screen.home.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pypypy.R
import com.example.pypypy.ui.screen.home.component.ProductItem

@Preview(showBackground = true)
@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .padding(top = 35.dp)
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                IconButton(onClick = {}) {
                    Icon(painter = painterResource(R.drawable.direction_left), contentDescription = null)
                }

                Text(text = "Популярное",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                )
            }
        }
    ) {
            paddingValues ->
        PopularScreenContent(paddingValues = paddingValues);
    }
}

@Composable
fun PopularScreenContent(
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier.padding(paddingValues = paddingValues),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        
    }
}

//Column(Modifier.background(Color(0xF7F7F9))) {
//    ProductItem(
//        title = "Best Seller",
//        name = "Nike Air Max",
//        price = "₽752.00",
//        imageRes = painterResource(R.drawable.nadejda),
//        onClick = {}
//    )
//}