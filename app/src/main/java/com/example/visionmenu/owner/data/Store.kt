package com.example.visionmenu.owner.data

import kotlinx.serialization.Serializable

@Serializable
data class Store(
    val store_id: Int? = null,
    val owner_id: Int,
    val store_name: String,
    val category: String,
    val address: String,
    val phone: String? = null,
    val business_hours: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val created_at: String? = null
)
