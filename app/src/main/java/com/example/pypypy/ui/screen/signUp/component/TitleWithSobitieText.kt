package com.example.pypypy.ui.screen.signUp.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pypypy.ui.theme.MatuleTheme

@Composable
fun TitleWithSubtitleText(title: String, subTitle:String){
    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            style = MatuleTheme.tupography.headingBold32.copy(color = MatuleTheme.colors.text),
            textAlign = TextAlign.Center
        )
        Text(
            text = subTitle,
            maxLines = 2,
            style = MatuleTheme.tupography.subTitleRegular16.copy(color = MatuleTheme.colors.subTextDark),
            textAlign = TextAlign.Center
        )
    }
}