package com.example.pypypy.ui.screen.signUp.regist


import AuthButton
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pypypy.R
import com.example.pypypy.ui.screen.signUp.component.AuthTextField
import com.example.pypypy.ui.screen.signUp.component.TitleWithSubtitleText
import com.example.pypypy.ui.theme.MatuleTheme

import org.koin.compose.viewmodel.koinViewModel


@Composable
fun RegistorScreen(onNavigationToSignScreen: () -> Unit) {

    val registrViewModel: RegistrViewModel = koinViewModel<RegistrViewModel>()
    Scaffold(
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
                        contentDescription = null)
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
                Text(
                    text = "Есть аккаунт? Войти",
                    style = MatuleTheme.tupography.bodyRegular16.copy(color = MatuleTheme.colors.text),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .clickable(
                            onClick = onNavigationToSignScreen,
                            role = Role.Button,
                            indication = LocalIndication.current,
                            interactionSource = remember { MutableInteractionSource() }
                        )
                        .padding(8.dp)
                )
            }
        }
    ) { paddingValues ->
        RegistorInContent(paddingValues,registrViewModel)
    }
}

@Composable
fun RegistorInContent(paddingValues: PaddingValues,
                      viewModel: RegistrViewModel
) {
    val regState = viewModel.signInState

    
    Column (modifier = Modifier.padding(paddingValues = paddingValues))
    {
        TitleWithSubtitleText(
            title = stringResource(R.string.registTitle),
            subTitle = stringResource(R.string.RegistSubTitle)
        )

        AuthTextField(
            value = regState.value.name,
            onChangeValue = {
                viewModel.setName(it)
            },
            isError = viewModel.nameHasError.value,
            placeholder = {
                Text(text = stringResource(R.string.registrName))
            },
            supportingText = {
                Text(text = stringResource(R.string.uncorrectName))
            },
            label = {
                Text(text = stringResource(R.string.userName))
            }

        )

        AuthTextField(
            value = regState.value.email,
            onChangeValue = {
                viewModel.setEmail(it)
            },
            isError = viewModel.emailHasError.value,
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


        AuthTextField(
            value = regState.value.password,
            onChangeValue = {
                viewModel.setPassword(it)
            },
            isError = false,
            placeholder = {
                Text(text = stringResource(R.string.maskPassword))
            },
            supportingText = {
                Text(text = stringResource(R.string.uncorrtect_password))
            },
            label = {
                Text(text = stringResource(R.string.password))
            }
        )
        val checkedState = remember { mutableStateOf(true) }
        Row (
            modifier = Modifier.padding(15.dp)
        ){
            Checkbox(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it },
                colors = CheckboxDefaults.colors(checkedColor = MatuleTheme.colors.background, checkmarkColor = Color.Black)

            )
            Text("Даю согласие на обработку персональных данных", fontSize = 16.sp,
                modifier = Modifier
                    .clickable(
                        onClick = {},
                        role = Role.Button,
                        indication = LocalIndication.current,
                        interactionSource = remember { MutableInteractionSource() }
                    )
                    .padding(4.dp),
                textDecoration = TextDecoration.Underline,
                style = MatuleTheme.tupography.subTitleRegular16.copy(color = MatuleTheme.colors.subTextDark))
        }

        val coroutine = rememberCoroutineScope()

        AuthButton(
            onClick = {
                viewModel.registration()
            }
        ) {
            Text(stringResource(R.string.regist))
        }

    }
}



