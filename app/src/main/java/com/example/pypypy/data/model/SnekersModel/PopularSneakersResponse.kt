package com.example.pypypy.data.model.SnekersModel

data class PopularSneakersResponse(
    val id: Int,
    var productName: String,
    var cost: String,
    var count: Int,
    var photo: String,
    var description: String
)
