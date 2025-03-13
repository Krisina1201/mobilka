package com.example.pypypy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.pypypy.ui.screen.regist.RegistorScreen
//import com.example.pypypy.ui.screen.signin.ForgotPassword
//import com.example.pypypy.ui.screen.signin.RegiostorScreen
//
// import com.example.pypypy.ui.screen.signin.TitleWithSobtitleText
import com.example.pypypy.ui.theme.MatuleTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MatuleTheme {
                //SignInScreen()
                RegistorScreen()
                //ForgotPass();
            }
        }
    }
}
