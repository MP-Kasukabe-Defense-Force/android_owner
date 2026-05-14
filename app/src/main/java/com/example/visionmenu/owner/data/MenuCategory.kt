package com.example.visionmenu.owner.data

import kotlinx.serialization.Serializable

@Serializable
data class MenuCategory(
    val category_id: Int? = null,
    val store_id: Int,
    val name: String,
    val sort_order: Int = 0
)
