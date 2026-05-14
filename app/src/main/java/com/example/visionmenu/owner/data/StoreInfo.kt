package com.example.visionmenu.owner.data

import kotlinx.serialization.Serializable

@Serializable
data class StoreInfo(
    val info_id: Int? = null,
    val store_id: Int,
    val entrance_desc: String? = null,
    val restroom_desc: String? = null,
    val kiosk_yesno: Boolean = false,
    val kiosk_desc: String? = null,
    val counter_desc: String? = null,
    val table_desc: String? = null,
    val extra_desc: String? = null
)
