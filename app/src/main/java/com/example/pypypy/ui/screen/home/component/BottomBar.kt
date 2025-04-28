package com.example.pypypy.ui.screen.home.component

import android.widget.ImageButton
import androidx.compose.material3.IconButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pypypy.R
import com.example.pypypy.ui.theme.MatuleTheme

@Composable
fun BottomBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(136.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.vector_1789),
            contentDescription = "нижняя панель",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp, start = 20.dp, end = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(42.dp)) {
                IconButton(onClick = {}) {
                    Image(painterResource(R.drawable.home), "Дом", Modifier.size(24.dp))
                }
                IconButton(onClick = {}) {
                    Image(painterResource(R.drawable.heart), "Избранное", Modifier.size(24.dp))
                }
            }

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 30.dp)
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            MatuleTheme.colors.accent,
                            shape = RoundedCornerShape(percent = 50)
                        )
                ) {
                    Image(painterResource(R.drawable.bag_2), "Корзина")
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(40.dp)) {
                IconButton(onClick = {}) {
                    Image(painterResource(R.drawable.colocol), "Уведомления", Modifier.size(24.dp))
                }
                IconButton(onClick = {}) {
                    Image(painterResource(R.drawable.people), "Профиль", Modifier.size(24.dp))
                }
            }
        }
    }
}