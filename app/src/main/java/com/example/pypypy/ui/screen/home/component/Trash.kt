//package com.example.pypypy.ui.screen.home.component
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.gestures.Orientation
//import androidx.compose.foundation.gestures.detectTapGestures
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.Delete
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.input.pointer.pointerInput
//import androidx.compose.ui.unit.IntOffset
//import androidx.compose.ui.unit.dp
//import androidx.compose.material.*
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.wear.compose.material.ExperimentalWearMaterialApi
//import androidx.wear.compose.material.FractionalThreshold
//import androidx.wear.compose.material.rememberSwipeableState
//import androidx.wear.compose.material.swipeable
//import kotlin.math.roundToInt
//
//// Добавляем класс Product
//data class Product(val name: String, val price: String)
//
//@OptIn(ExperimentalWearMaterialApi::class)
//@Composable
//fun SwipeableProductItem(
//    product: Product,
//    onAdd: () -> Unit = {},
//    onRemove: () -> Unit = {}
//) {
//    val swipeableState = rememberSwipeableState(initialValue = 0)
//    val anchors = mapOf(0f to 0, -100f to -1, 100f to 1) // -1 = left, 1 = right
//
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(80.dp)
//    ) {
//        // Фон при свайпе
//        Row(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            // Левая часть (добавление)
//            Box(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .width(100.dp)
//                    .background(Color.Green),
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(Icons.Default.Add, "Добавить", tint = Color.White)
//            }
//
//            // Правая часть (удаление)
//            Box(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .width(100.dp)
//                    .background(Color.Red),
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(Icons.Default.Delete, "Удалить", tint = Color.White)
//            }
//        }
//
//        // Основная карточка товара
//        Box(
//            modifier = Modifier
//                .swipeable(
//                    state = swipeableState,
//                    anchors = anchors,
//                    thresholds = { _, _ -> FractionalThreshold(0.3f) },
//                    orientation = Orientation.Horizontal // Исправлено на правильный Orientation
//                )
//                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
//                .fillMaxWidth()
//                .height(80.dp)
//                .background(MaterialTheme.colorScheme.surface)
//                .pointerInput(Unit) {
//                    detectTapGestures {
//                        swipeableState.animateTo(0) // Возврат при тапе
//                    }
//                }
//        ) {
//            ProductContent(product)
//        }
//    }
//
//    // Обработка завершения свайпа
//    LaunchedEffect(swipeableState.currentValue) {
//        when (swipeableState.currentValue) {
//            -1 -> { onAdd(); swipeableState.animateTo(0) }
//            1 -> { onRemove() }
//        }
//    }
//}
//
//@Composable
//fun ProductContent(product: Product) {
//    Row(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(horizontal = 16.dp),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text(
//            text = product.name,
//            style = MaterialTheme.typography.bodyLarge // Исправлено для Material 3
//        )
//        Text(
//            text = product.price,
//            style = MaterialTheme.typography.titleMedium, // Исправлено для Material 3
//            color = MaterialTheme.colorScheme.primary // Исправлено для Material 3
//        )
//    }
//}
//
//@Preview
//@Composable
//fun TrashPreview() {
//    MaterialTheme {
//        SwipeableProductItem(
//            product = Product("Nike Club Max", "P584.95"),
//            onAdd = { /* обработка добавления */ },
//            onRemove = { /* обработка удаления */ }
//        )
//    }
//}