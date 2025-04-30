package com.example.pypypy.ui.screen.signUp.signin


import AuthButton
import androidx.compose.foundation.LocalIndication
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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import com.example.pypypy.ui.theme.MatuleTheme
import androidx.compose.ui.unit.dp
import com.example.pypypy.R
import com.example.pypypy.ui.screen.signUp.component.AuthTextField
import com.example.pypypy.ui.screen.signUp.component.TitleWithSubtitleText
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun SignInScreen(onNavigationToScreenPassword: () -> Unit,
                 onNavigationToScreenRegistor: () -> Unit,
                 onNavigationToHome: () -> Unit) {


    val SignInViewModel: SignInViewModel = koinViewModel<SignInViewModel>()
    
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .padding(top = 35.dp)
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                IconButton(onClick = {}) {
                    Icon(painter = painterResource(R.drawable.direction_left), contentDescription = null)
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
                    text = stringResource(R.string.addUser),
                    style = MatuleTheme.tupography.bodyRegular16.copy(color = MatuleTheme.colors.text),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .clickable(
                            onClick = onNavigationToScreenRegistor,
                            role = Role.Button,
                            indication = LocalIndication.current,
                            interactionSource = remember { MutableInteractionSource() }
                        )
                        .padding(8.dp)
                )
            }
        }
    ) { paddingValues ->
        SignInContent(paddingValues, onNavigationToScreenPassword = onNavigationToScreenPassword, viewModel = SignInViewModel, onNavigationToHome= onNavigationToHome)
    }
}

@Composable
fun SignInContent(
    paddingValues: PaddingValues,
    onNavigationToScreenPassword: () -> Unit,
    onNavigationToHome: () -> Unit,
    viewModel: SignInViewModel
) {
    val signInState = viewModel.signInState
    Column(
        modifier = Modifier.padding(paddingValues = paddingValues),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TitleWithSubtitleText(
            title = stringResource(R.string.hello),
            subTitle = stringResource(R.string.sign_in_sobitie)
        )

        AuthTextField(
            value = signInState.value.email,
            onChangeValue = { viewModel.setEmail(it) },
            isError = viewModel.emailHasError.value,
            placeholder = { Text(text = stringResource(R.string.emailHasError)) },
            supportingText = { Text(text = stringResource(R.string.uncorrect_email)) },
            label = { Text(text = stringResource(R.string.email)) }
        )

        AuthTextField(
            value = signInState.value.password,
            onChangeValue = { viewModel.setPassword(it) },
            isError = false,
            placeholder = { Text(text = stringResource(R.string.password_template)) },
            supportingText = { Text(text = stringResource(R.string.uncorrtect_password)) },
            label = { Text(text = stringResource(R.string.password)) }
        )

        Text(
            text = "Забыли пароль?",
            style = MatuleTheme.tupography.bodyRegular16.copy(color = MatuleTheme.colors.text),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clickable(
                    onClick = onNavigationToScreenPassword,
                    role = Role.Button,
                    indication = LocalIndication.current,
                    interactionSource = remember { MutableInteractionSource() }
                )
                .padding(8.dp)
        )

        val coroutine = rememberCoroutineScope()

        AuthButton(onClick = {
                viewModel.sighIn()
        }) {
            Text(stringResource(R.string.sign_in))
        }


            LaunchedEffect(signInState.value){
                if (signInState.value.isSignIn) {
                    onNavigationToHome()
                    signInState.value.isSignIn = false
                }
            }
    }
}


//AuthButton(
//onClick = {
//    coroutine.launch {
//        val registRequest = RegistrationRequest (
//            email = regState.value.email,
//            password = regState.value.password,
//            userName = regState.value.name
//        )
//
//        repository.registration(registRequest)
//    }
//}
//) {
//    Text(stringResource(R.string.regist))
//}

