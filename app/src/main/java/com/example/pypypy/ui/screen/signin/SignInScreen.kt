package com.example.pypypy.ui.screen.signin


import AuthButton
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.pypypy.ui.theme.MatuleTheme
import androidx.compose.ui.unit.dp
import com.example.pypypy.R
import com.example.pypypy.ui.common.CommonButton
import com.example.pypypy.ui.screen.component.AuthTextField
import com.example.pypypy.ui.screen.component.TitleWithSubtitleText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SignInScreen(){
    val signInViewModel: SignInViewModel = viewModel()
    Scaffold (
        topBar = {

            Row(
                modifier = Modifier
                    .padding(top = 35.dp)
                    .fillMaxWidth()
                    .height(40.dp)
            ){
                IconButton(onClick = {}) {
                    Icon(painter = painterResource(R.drawable.direction_left),
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

            ){
                Text(
                    text = stringResource(R.string.addUser),
                    style = MatuleTheme.tupography.bodyRegular16.copy(color = MatuleTheme.colors.text),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .clickable(
                            onClick = {},
                            role = Role.Button,
                            indication = LocalIndication.current,
                            interactionSource = remember { MutableInteractionSource() }
                        )
                        .padding(8.dp)
                )
                }
            }
    ) {
        paddingValues ->
        SignInContent(paddingValues, signInViewModel)
    }

}

@Composable
fun SignInContent(paddingValues: PaddingValues, signInViewModel: SignInViewModel){
    val signInState = signInViewModel.signInState
    Column (
        modifier = Modifier.padding(paddingValues = paddingValues),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TitleWithSubtitleText(
            title = stringResource(R.string.hello),
            subTitle = stringResource(R.string.sign_in_sobitie)
        )

        AuthTextField(
            value = signInState.value.email,
            onChangeValue = {
                signInViewModel.setEmail(it)
            },
            isError = signInViewModel.emailHasError.value,
            placeholder = {
                Text(text = stringResource(R.string.emailHasError))
            },
            supportingText = {
                Text(text = stringResource(R.string.uncorrect_email))
            },
            label = {
                Text(text = stringResource(R.string.email))
            }
        )

        AuthTextField(
            value = signInState.value.password,
            onChangeValue = {
                signInViewModel.setPassword(it)
            },
            isError = false,
            placeholder = {
                Text(text = stringResource(R.string.password_template))
            },
            supportingText = {
                Text(text = stringResource(R.string.uncorrtect_password))
            },
            label = {
                Text(text = stringResource(R.string.password))
            }
        )
        AuthButton(
            onClick = {}
        ) {
            Text(stringResource(R.string.sign_in))
        }

    }
}




