package com.example.pypypy.ui.screen.hello.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun text(title: String) {
    Text(
        text = title, fontSize = 30.sp, fontWeight = FontWeight(600), color = Color.White
    )
}