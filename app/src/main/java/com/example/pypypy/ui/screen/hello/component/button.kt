package com.example.pypypy.ui.screen.hello.component

import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.pypypy.ui.screen.signUp.common.CommonButton
import com.example.pypypy.ui.theme.MatuleTheme

@Composable
fun button(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    CommonButton(
        onClick = onClick,
        buttonColors = ButtonColors(
            contentColor = Color.Black,
            containerColor = Color.White,
            disabledContentColor = MatuleTheme.colors.accent,
            disabledContainerColor = MatuleTheme.colors.accent
        ),
    ){
        content()
    }
}