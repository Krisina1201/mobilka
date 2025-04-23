package com.example.pypypy.ui.screen.signUp.forgotPassword

import AuthButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pypypy.R
import com.example.pypypy.ui.screen.signUp.component.AuthTextField
import com.example.pypypy.ui.screen.signUp.component.TitleWithSubtitleText
import com.example.pypypy.ui.theme.MatuleTheme

@Composable
fun ForgotPass() {
    val signInViewModel: ForgotPasswordViewModel = viewModel()
    Scaffold (
        topBar = {
            Row(
                modifier = Modifier
                    .padding(top = 35.dp)
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.direction_left),
                        contentDescription = null
                    )
                }
            }
        },
        bottomBar = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 50.dp)
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
            }
        }
    )
    { paddingValues ->
        Content(paddingValues, signInViewModel)
    }
}

@Composable
fun Content (paddingValues: PaddingValues,
             signInViewModel: ForgotPasswordViewModel
) {

    val signInState = signInViewModel.signInState

    Column(modifier = Modifier.padding(paddingValues = paddingValues))
    {
        Row(modifier = Modifier.padding(14.dp))
        {
            TitleWithSubtitleText(
                title = stringResource(R.string.forgotPassword1),
                subTitle = stringResource(R.string.forgotPassword2)
            )
        }

        Row(modifier = Modifier.padding(14.dp))
        {

            AuthTextField(
                value = signInState.value.email,
                onChangeValue = {
                    signInViewModel.setEmail(it)
                },
                isError = signInViewModel.emailHasError.value,
                placeholder = {
                    Text(text = stringResource(R.string.box_email))
                },
                supportingText = {
                    Text(text = stringResource(R.string.uncorrect_email))
                },
                label = {
                    Text(text = stringResource(R.string.email))
                }
            )
        }

        Row (modifier = Modifier.padding(14.dp))
        {
            AuthButton(
                onClick = {}
            ) {
                Text(stringResource(R.string.forgotPasswordButton))
            }
        }
    }
}