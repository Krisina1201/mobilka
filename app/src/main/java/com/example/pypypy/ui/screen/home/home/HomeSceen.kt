package com.example.pypypy.ui.screen.home.home

import android.widget.ImageButton
import androidx.compose.material3.IconButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.pypypy.R
import com.example.pypypy.ui.screen.home.component.BottomBar
import com.example.pypypy.ui.screen.home.component.TopPanel
import com.example.pypypy.ui.screen.home.popylar.PopularScreenContent
import com.example.pypypy.ui.screen.home.popylar.PopylarSneakersViewModel
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pypypy.ui.screen.home.component.ProductItem
import com.example.pypypy.ui.screen.home.favourite.FavouriteScreenViewModel
import com.example.pypypy.ui.theme.MatuleTheme

data class Name(
    val name: String
)
@Composable
fun HomeScreenHast(onNavigationToPopylarScreen: () -> Unit,
                   onNavigationToSortScreen: (String) -> Unit,
                   onNavigationToFavouriteScreen: () -> Unit,
                   onNavigationToTrashScreen: () -> Unit,
                   onNavigationToFilterScreen: () -> Unit) {
    val sneakersViewModel: PopylarSneakersViewModel = koinViewModel<PopylarSneakersViewModel>()

    Scaffold(
        topBar = {
            TopPanel(title = "Главная",
                leftImage = painterResource(R.drawable.menu),
                rightImage = painterResource(R.drawable.black_shop),
                textSize = 32
            )
        },

        bottomBar = { BottomBar(onNavigationToFavouriteScreen,
            backgroundHomeImage = painterResource(R.drawable.home_blue),
            backgroundFavouriteImage = painterResource(R.drawable.heart),
            homeClick = {},
            trashClick = onNavigationToTrashScreen
        ) }
    ) {
        paddingValues ->
        HomeScreenContent(paddingValues = paddingValues, viewModel = sneakersViewModel, onNavigationToPopylarScreen, onNavigationToSortScreen, onNavigationToFilterScreen);
    }
}

@Composable
fun HomeScreenContent(
    paddingValues: PaddingValues,
    viewModel: PopylarSneakersViewModel,
    onNavigationToPopylarScreen: () -> Unit,
    onNavigationToSortScreen: (String) -> Unit,
    onNavigationToFilterScreen: () -> Unit
) {
    val message = remember{mutableStateOf("")}
    val aaa: List<Name>

    Column(
        modifier = Modifier.padding(paddingValues = paddingValues).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 15.dp, top = 35.dp)
        ) {
            TextField(
                value = message.value,
                onValueChange = {newText -> message.value = newText},
                placeholder = { Text("Поиск") },
                modifier = Modifier
                    .weight(1f)
                    .requiredHeight(52.dp)

                ,
                leadingIcon = {Icon(painterResource(R.drawable.check), contentDescription = "Поиск")},
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MatuleTheme.colors.block,
                    disabledContainerColor = MatuleTheme.colors.background,
                    unfocusedContainerColor = MatuleTheme.colors.background,
                    errorContainerColor = MatuleTheme.colors.background,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Red
                )
            )
            IconButton(
                onClick = onNavigationToFilterScreen,
                modifier = Modifier
                    .size(52.dp)
                    .background(
                        MatuleTheme.colors.accent,
                        shape = RoundedCornerShape(percent = 50)
                    )
            ) {
                Image(painterResource(R.drawable.sliders), "Сортировка")
            }

        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(bottom = 24.dp)
        ) {
            Text(text = "Категории",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp, modifier = Modifier.padding(bottom = 16.dp, top = 24.dp))

            val langs = listOf("Всё", "Outdoor", "Tennis", "Podcrdyli")


            LazyRow {
                items(langs) { lang ->
                    
                    Button(
                        onClick = {
                            onNavigationToSortScreen(lang)
                        },
                        modifier = Modifier
                            .size(width = 108.dp, height = 40.dp)
                            .padding(end = 16.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.Black,
                            containerColor = Color.White
                        )
                    ){
                        Text(text = lang, fontWeight = FontWeight.Normal, fontSize = 12.sp)
                    }
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                Text(text = "Популярное", fontWeight = FontWeight.Normal, fontSize = 16.sp)
                Box (

                ){
                    Text(text = "Все", fontSize = 12.sp, color = MatuleTheme.colors.accent, modifier = Modifier.padding(top = 3.5.dp, start = 258.dp).
                    clickable(
                        onClick = onNavigationToPopylarScreen,
                        role = Role.Button,
                        indication = LocalIndication.current,
                        interactionSource = remember { MutableInteractionSource() }
                    ))
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                ProductItem(
                    title = "Best Seller",
                    name = "Nike Air Max",
                    price = "₽752",
                    imageRes = painterResource(R.drawable.nadejda),
                    onClick = {},
                    likeImage = painterResource(R.drawable.icon),
                    likeClick = {},
                    cartImage = painterResource(R.drawable.group_1072),
                    cartClick = {}
                )

                Column(modifier = Modifier.fillMaxHeight().width(51.dp)) {}

                ProductItem(
                    title = "Best Seller",
                    name = "Nike Air Max",
                    price = "₽752",
                    imageRes = painterResource(R.drawable.nadejda),
                    onClick = {},
                    likeImage = painterResource(R.drawable.icon),
                    likeClick = {},
                    cartImage = painterResource(R.drawable.group_1000000808),
                    cartClick = {}
                )
            }

            Column (
                modifier = Modifier.fillMaxWidth().padding(top = 31.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 25.dp)
                ){
                    Text(text = "Акции", fontWeight = FontWeight.Medium, fontSize = 16.sp)
                    Text(text = "Все", fontSize = 12.sp, color = MatuleTheme.colors.accent, modifier = Modifier.padding(top = 3.5.dp, start = 303.dp))
                }

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(painter = painterResource(
                        R.drawable.rectangle_4231),
                        contentDescription = "основав",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Image(
                        painter = painterResource(R.drawable.sale),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.CenterStart)
                            .padding(start = 22.dp)
                    )

                    Image(
                        painter = painterResource(R.drawable.misc_06),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center)
                            .padding(start = 60.dp, bottom = 30.dp)
                    )

                    Image(
                        painter = painterResource(R.drawable.cale_sneakers),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.CenterEnd)
                            .padding(end = 57.dp)
                    )
                }
            }
        }
    }

}