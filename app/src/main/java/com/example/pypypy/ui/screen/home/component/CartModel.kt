package com.example.pypypy.ui.screen.home.component

data class CartModel(
    var shoeId: Int = 0,
    var inCart: Boolean = false,
    var quantity: Int = 1  // Добавляем поле для количества
)
