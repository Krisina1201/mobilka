package com.example.pypypy.ui.screen.home.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pypypy.R
import com.example.pypypy.ui.theme.MatuleTheme
import com.example.pypypy.ui.theme.MatuleTheme.colors


@Composable
fun ProductItem(
    title: String,
    name: String,
    price: String,
    imageRes: Painter,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(bottom = 8.dp),
        verticalArrangement = Arrangement.Top
    ) {
        // Image Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            ) {
                Image(
                    painter = imageRes,
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                        .padding(5.dp)
                )

                // Favorite Button
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    IconButton(
                        onClick = { /* Favorite action */ },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.icon),
                            contentDescription = "Favorite",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }

        // Text Content Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(top = 8.dp)
        ) {
            // Title
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = title,
                    color = colors.accent,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Product Name
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Price and Add to Cart
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Price
                    Column {
                        Text(
                            text = price,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    // Add to Cart Button
                    Column {
                        IconButton(
                            onClick = { /* Add to cart action */ },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.group_107),
                                contentDescription = "Add to cart",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}