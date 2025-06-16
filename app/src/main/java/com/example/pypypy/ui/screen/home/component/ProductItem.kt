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
    price: String,
    name:String,
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = likeClick,
                modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp)
                    //.background(Color.Yellow)
            ) {
                Image(
                    painter = likeImage,
                    contentDescription = "Favorite",
                    modifier = Modifier.size(28.dp).align(Alignment.TopStart),

                )
            }

            Image(
                painter = imageRes,
                contentDescription = "name",
                contentScale = ContentScale.Fit,
                alignment = Alignment.BottomCenter,
                modifier = Modifier
                    .width(114.dp)
                    .height(80.dp)
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp),
            )
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                color = Color(0xFF48B2E7),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp).padding(horizontal = 12.dp)
            )

            Text(
                text = name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp).padding(horizontal = 12.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = price,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 12.dp).padding(bottom = 12.dp)
                )
//                IconButton(
//                    onClick = cartClick,
//                    modifier = Modifier
//                        .padding(top=10.dp)
//                        //.size(24.dp)
//                        .background(Color.Green)
//                        .align(Alignment.BottomEnd)
//                ) {
//                    Image(
//                        painter = cartImage,
//                        contentDescription = "Cart",
//                        //modifier = Modifier.size(28.dp)
//                    )
//                }
                IconButton(
                    onClick = cartClick,
                    modifier = Modifier
                        .size(34.dp, 36.dp)
                        .background(
                            Color(0xFF48B2E7),
                            shape = RoundedCornerShape(percent = 20)
                        )
                        .align(Alignment.BottomEnd),
                        //.padding(end = 8.dp, bottom = 8.dp)  // Отступы от краев

                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(1f)
                    ) {
                        Image(
                            painter = cartImage,  // Ваша иконка корзины
                            contentDescription = "Добавить в корзину",
                            modifier = Modifier.size(27.dp)
                                .align(Alignment.TopStart)
                                .padding(top=7.dp)

                        )
                    }
                }
            }

        }
    }
}

//@Preview
//@Composable
//fun check () {
//    ProductItem(
//        title = "Best Seller",
//        price = "1000",
//        imageRes = painterResource( R.drawable.shoe2),
//        onClick = {},
//        likeImage = painterResource(R.drawable.icon),
//        likeClick = {},
//        cartImage = painterResource(R.drawable.group_1000000808),
//        cartClick = {},
//        name = "Nike"
//    )
//}

