package com.example.cabzo.model

sealed class BookingState {
    object Idle : BookingState()
    object SelectingDestination : BookingState()
    data class SelectingRide(
        val pickupLocation: String = "Current Location (Indiranagar)",
        val dropLocation: String = "Kempegowda Int'l Airport T2"
    ) : BookingState()
    
    data class SearchingDriver(
        val selectedRide: RideOption,
        val pickupLocation: String,
        val dropLocation: String
    ) : BookingState()

    data class DriverMatched(
        val driver: DriverDetails,
        val selectedRide: RideOption,
        val pickupLocation: String,
        val dropLocation: String
    ) : BookingState()

    data class TripInProgress(
        val driver: DriverDetails,
        val selectedRide: RideOption,
        val pickupLocation: String,
        val dropLocation: String,
        val progress: Float = 0.3f // 0.0 to 1.0
    ) : BookingState()

    data class TripCompleted(
        val driver: DriverDetails,
        val selectedRide: RideOption,
        val farePaid: String
    ) : BookingState()
}
