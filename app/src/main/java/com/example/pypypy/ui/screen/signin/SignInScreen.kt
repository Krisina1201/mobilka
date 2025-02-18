package com.example.pypypy.ui.screen.signin

import android.icu.text.CaseMap.Title
import android.inputmethodservice.Keyboard.Row
import android.view.textclassifier.TextClassificationSessionFactory
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.modifier.modifierLocalConsumer
import com.example.pypypy.ui.theme.MatuleTheme
import androidx.compose.ui.unit.dp

@Composable
fun SignInScreen(){
    SignInContent()
}



@Composable
fun TitleWithSobtitleText(title: String, subTitle: String) {
    Column (
        Modifier.padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    {
        Text(
            text = title,
            style = MatuleTheme.tupography.headingBold32.copy(color = MatuleTheme.colors.text)
        )
        Text(
            text = subTitle,
            maxLines = 2,
            style = MatuleTheme.tupography.subTitleRegular16.copy(color = MatuleTheme.colors.subTextDark),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun SignInContent(){
    Column {
        TitleWithSobtitleText(
            //title = androidx.compose.ui.res.stringResource(R.string.hello),
            title = "хуй",
            subTitle = androidx.compose.ui.res.stringResource(R.string.sign_in_subitile)
        )
    }
}



