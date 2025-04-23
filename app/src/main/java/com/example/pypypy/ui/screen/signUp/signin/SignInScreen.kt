package com.example.pypypy.ui.screen.signUp.signin


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
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import com.example.pypypy.ui.theme.MatuleTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pypypy.R
import com.example.pypypy.ui.screen.signUp.common.CommonButton
import com.example.pypypy.ui.screen.signUp.component.AuthTextField
import com.example.pypypy.ui.screen.signUp.component.TitleWithSubtitleText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pypypy.data.model.AuthRequest
import com.example.pypypy.data.model.RegistrationRequest
import com.example.pypypy.data.repository.AuthRepositoryImpl
import com.example.pypypy.domain.usecase.AuthUseCase
import com.example.pypypy.ui.screen.signUp.regist.RegistrViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
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
    var signInState = viewModel.signInState
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
        Button(
            onClick = onNavigationToHome
        ) {
            Text("кнопка")
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

