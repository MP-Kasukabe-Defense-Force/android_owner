package com.example.myapplication.data

import kotlinx.serialization.Serializable

@Serializable
data class Owner(
    val owner_id: Int? = null,
    val auth_id: String,
    val name: String,
    val created_at: String? = null
)
