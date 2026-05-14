package com.example.visionmenu.owner.data

import kotlinx.serialization.Serializable

@Serializable
data class MenuItem(
    val item_id: Int? = null,
    val category_id: Int,
    val name: String,
    val price: Int,
    val description: String? = null,
    val allergy_info: String? = null,
    val sort_order: Int = 0
)
