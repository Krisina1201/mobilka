package com.example.pypypy.ui.theme

//import android.graphics.Color
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.pypypy.R


@Immutable
data class MutColors (
    val block: Color,
    val text: Color,
    val subTextDark: Color,
    val background: Color,
    val hint: Color,
    val accent: Color,
    )

@Immutable
data class MatubleTextStyle (
    val headingBold32: TextStyle,
    val subTitleRegular16: TextStyle,
    val bodyRegular16: TextStyle,
    val bodyRegular14: TextStyle,
    val bodyRegular12: TextStyle )

val localMatuleTypography = staticCompositionLocalOf {
    MatubleTextStyle(
        headingBold32 = TextStyle.Default,
        subTitleRegular16 = TextStyle.Default,
        bodyRegular16 = TextStyle.Default,
        bodyRegular14 = TextStyle.Default,
        bodyRegular12 = TextStyle.Default
    )
}

val localMatybaleColor = staticCompositionLocalOf {
    MutColors (
        block = Color.Unspecified,
        text = Color.Unspecified,
        subTextDark = Color.Unspecified,
        background = Color.Unspecified,
        hint = Color.Unspecified,
        accent = Color.Unspecified
    )
}

val fontFamily = FontFamily(
    Font(R.font.roboto_serif, FontWeight.Normal),
    Font(R.font.roboto_serif_bold, FontWeight.Bold),
    Font(R.font.roboto_serif_black, FontWeight.Black),
    Font(R.font.roboto_serif_medium, FontWeight.Medium),
    Font(R.font.roboto_serif_extrabold, FontWeight.ExtraBold),
    Font(R.font.roboto_serif_semibold, FontWeight.SemiBold),
)

@Composable
fun MatuleTheme(content: @Composable () -> Unit) {
    val matuleColors = MutColors(
        block = Color(0xFFFFFFFF),
        text = Color(0xFF2B2B2B),
        subTextDark = Color(0xFF707B81),
        background = Color(0xFFF7F7F9),
        hint = Color(0xFF6A6A6A),
        accent = Color(0xFF48B2E7)
    )
    val matuleTypography = MatubleTextStyle (
        headingBold32 = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Bold, fontSize = 32.sp),
        subTitleRegular16 = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp),
        bodyRegular16 = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp),
        bodyRegular14 = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 14.sp),
        bodyRegular12 = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 12.sp)
    )
    CompositionLocalProvider(
        localMatybaleColor provides matuleColors,
        localMatuleTypography provides matuleTypography,
        content = content
    )
}

object MatuleTheme {
    val colors: MutColors
    @Composable
    get() = localMatybaleColor.current
    val tupography
    @Composable
    get() = localMatuleTypography.current
}

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun NetworkSampleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}