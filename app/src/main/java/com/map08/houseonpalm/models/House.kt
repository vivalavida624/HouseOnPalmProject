package com.map08.houseonpalm.models

data class House(
    val address: String = "",
    val numberOfRooms: Int = 0,
    val price: Double = 0.0,
    val isForSale: Boolean = false,
    val broken: String = "",
    val imageUrl: String = "",
)