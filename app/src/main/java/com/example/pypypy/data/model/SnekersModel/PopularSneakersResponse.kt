package com.example.pypypy.data.model.SnekersModel

import kotlinx.serialization.Serializable

@Serializable
data class PopularSneakersResponse(
    val id: Int,
    var productName: String,
    var cost: String,
    var count: Int,
    var photo: String,
    var description: String,
    var category: String
)
