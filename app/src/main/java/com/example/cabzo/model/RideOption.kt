package com.example.cabzo.model

import androidx.compose.ui.graphics.vector.ImageVector

data class RideOption(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val originalPrice: Double? = null,
    val etaMinutes: Int,
    val capacity: Int,
    val rideType: RideCategory,
    val badge: String? = null
)

enum class RideCategory {
    MINI,
    SEDAN,
    XL,
    AUTO,
    BIKE,
    LUXURY
}

val defaultRideOptions = listOf(
    RideOption(
        id = "mini",
        name = "Cabzo Mini",
        description = "Pocket-friendly, comfy hatchbacks",
        price = 149.0,
        originalPrice = 180.0,
        etaMinutes = 2,
        capacity = 4,
        rideType = RideCategory.MINI,
        badge = "POPULAR"
    ),
    RideOption(
        id = "sedan",
        name = "Cabzo Sedan",
        description = "Top-rated sedans with extra legroom",
        price = 199.0,
        etaMinutes = 4,
        capacity = 4,
        rideType = RideCategory.SEDAN,
        badge = "RECOMMENDED"
    ),
    RideOption(
        id = "auto",
        name = "Cabzo Auto",
        description = "Quick 3-wheelers through traffic",
        price = 89.0,
        originalPrice = 105.0,
        etaMinutes = 3,
        capacity = 3,
        rideType = RideCategory.AUTO,
        badge = "FASTEST"
    ),
    RideOption(
        id = "bike",
        name = "Cabzo Moto",
        description = "Single rider, beat city jams fast",
        price = 49.0,
        etaMinutes = 2,
        capacity = 1,
        rideType = RideCategory.BIKE,
        badge = "CHEAPEST"
    ),
    RideOption(
        id = "xl",
        name = "Cabzo XL",
        description = "Spacious 6-seaters for family & luggage",
        price = 289.0,
        etaMinutes = 6,
        capacity = 6,
        rideType = RideCategory.XL
    ),
    RideOption(
        id = "luxury",
        name = "Cabzo Premier",
        description = "High-end luxury sedans & top drivers",
        price = 449.0,
        etaMinutes = 7,
        capacity = 4,
        rideType = RideCategory.LUXURY,
        badge = "PREMIUM"
    )
)
