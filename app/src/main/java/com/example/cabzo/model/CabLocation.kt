package com.example.cabzo.model

data class CabLocation(
    val id: String,
    val xRatio: Float,     // Normalized 0.0 .. 1.0 on map width
    val yRatio: Float,     // Normalized 0.0 .. 1.0 on map height
    val angle: Float,      // Heading in degrees
    val type: RideCategory
)

data class DriverDetails(
    val name: String,
    val rating: Float,
    val totalRides: Int,
    val vehicleModel: String,
    val vehicleNumber: String,
    val vehicleColor: String,
    val phoneNumber: String,
    val otp: String,
    val etaMinutes: Int
)

val sampleDriver = DriverDetails(
    name = "Rajesh Verma",
    rating = 4.9f,
    totalRides = 1420,
    vehicleModel = "White Swift Dzire",
    vehicleNumber = "KA 01 EQ 8899",
    vehicleColor = "White",
    phoneNumber = "+91 98765 43210",
    otp = "4829",
    etaMinutes = 3
)
