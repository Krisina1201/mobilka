package com.example.pypypy.ui.screen.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.ButtonDefaults.shape

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.pypypy.data.model.SnekersModel.PopularSneakersResponse
import com.example.pypypy.ui.theme.MatuleTheme
import kotlin.math.roundToInt

@Composable
fun SwipeableProductItem(
    sneakers: PopularSneakersResponse,
    quantity: Int,
    onClick: () -> Unit,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFF48B2E7), RoundedCornerShape(8.dp))
                .width(60.dp)
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(onClick = onIncrease) {
                Text("+", color = Color.White, fontSize = 18.sp)
            }
            Text("$quantity", color = Color.White, fontSize = 16.sp)
            IconButton(onClick = onDecrease) {
                Text("-", color = Color.White, fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = onClick,
            modifier = Modifier
                .width(250.dp)
                .height(120.dp),
            colors = buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(com.example.pypypy.R.drawable.nadejda),
                    contentDescription = "Sneaker Image",
                    modifier = Modifier
                        .width(100.dp)
                        .height(70.dp),
                )
                Column {
                    Text(
                        text = sneakers.productName,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(5.dp),
                        color = Color.DarkGray
                    )
                    Text(
                        text = sneakers.cost,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(5.dp),
                        color = Color.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .background(Color.Red, RoundedCornerShape(8.dp))
                .height(120.dp)
                .width(60.dp)
                .size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Удалить",
                    tint = Color.White
                )
            }
        }
    }
}