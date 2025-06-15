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


// ProductItem.kt
@Composable
fun ProductItem(
    title: String,
    name: String,
    price: String,
    imageRes: Painter,
    onClick: () -> Unit,
    likeImage: Painter,
    likeClick: () -> Unit,
    cartImage: Painter,
    cartClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .height(186.5.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable(onClick = onClick)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = likeClick,
                modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp)
            ) {
                Image(
                    painter = likeImage,
                    contentDescription = "Favorite",
                    modifier = Modifier.size(28.dp)
                )
            }

            IconButton(
                onClick = cartClick,
                modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp)
            ) {
                Image(
                    painter = cartImage,
                    contentDescription = "Cart",
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Image(
            painter = imageRes,
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(114.dp, 53.dp).padding(horizontal = 12.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp)
        ) {
            Text(
                text = title,
                color = colors.accent,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = price,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

