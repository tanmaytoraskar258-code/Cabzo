package com.example.cabzo.model

data class TripHistory(
    val id: String,
    val pickupLocation: String,
    val dropLocation: String,
    val date: String,
    val time: String,
    val fare: String,
    val rideType: String,
    val driverName: String,
    val vehicleNumber: String,
    val rating: Float,
    val status: TripStatus
)

enum class TripStatus {
    COMPLETED,
    CANCELLED,
    ONGOING
}

val sampleTripHistory = listOf(
    TripHistory(
        id = "CBZ-98211",
        pickupLocation = "Indiranagar 100ft Road, Bengaluru",
        dropLocation = "Kempegowda Int'l Airport T2",
        date = "Today",
        time = "08:30 AM",
        fare = "₹749.00",
        rideType = "Cabzo Sedan",
        driverName = "Rajesh Kumar",
        vehicleNumber = "KA 01 EQ 8899",
        rating = 4.9f,
        status = TripStatus.COMPLETED
    ),
    TripHistory(
        id = "CBZ-97420",
        pickupLocation = "Koramangala 5th Block",
        dropLocation = "MG Road Metro Station",
        date = "Yesterday",
        time = "06:15 PM",
        fare = "₹149.00",
        rideType = "Cabzo Mini",
        driverName = "Anil Sharma",
        vehicleNumber = "KA 05 MN 4321",
        rating = 5.0f,
        status = TripStatus.COMPLETED
    ),
    TripHistory(
        id = "CBZ-96301",
        pickupLocation = "HSR Layout Sector 2",
        dropLocation = "Phoenix Marketcity Mall",
        date = "12 Oct",
        time = "02:45 PM",
        fare = "₹89.00",
        rideType = "Cabzo Auto",
        driverName = "Suresh V",
        vehicleNumber = "KA 03 GH 1209",
        rating = 4.8f,
        status = TripStatus.COMPLETED
    ),
    TripHistory(
        id = "CBZ-95112",
        pickupLocation = "Whitefield Main Road",
        dropLocation = "Tech Park Tower B",
        date = "10 Oct",
        time = "09:10 AM",
        fare = "₹49.00",
        rideType = "Cabzo Moto",
        driverName = "Vikram Singh",
        vehicleNumber = "KA 53 TR 9988",
        rating = 4.7f,
        status = TripStatus.COMPLETED
    )
)
