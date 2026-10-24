package com.example.cabzo.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cabzo.model.*
import com.example.cabzo.ui.components.*
import com.example.cabzo.ui.theme.*

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    var bookingState by remember { mutableStateOf<BookingState>(BookingState.Idle) }
    var pickupLocation by remember { mutableStateOf("Indiranagar 100ft Rd, Bengaluru") }
    var dropLocation by remember { mutableStateOf("") }
    var selectedRideOption by remember { mutableStateOf(defaultRideOptions.first()) }
    var selectedPaymentMethod by remember { mutableStateOf("Cabzo Wallet (₹540)") }
    var isSearchSheetOpen by remember { mutableStateOf(false) }
    var showTripCompletedDialog by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        // 1. Simulated Interactive Animated Map
        CabzoMap(
            bookingState = bookingState,
            modifier = Modifier.fillMaxSize()
        )

        // 2. Top Location Search Trigger
        if (bookingState is BookingState.Idle || bookingState is BookingState.SelectingRide) {
            LocationSearchBar(
                pickupLocation = pickupLocation,
                dropLocation = dropLocation,
                onDestinationSelected = { dropLocation = it },
                onOpenSearch = { isSearchSheetOpen = true },
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .align(Alignment.TopCenter)
            )
        }

        // 3. Bottom Booking Sheet (Ride Selection & Booking)
        AnimatedVisibility(
            visible = bookingState is BookingState.SelectingRide,
            enter = slideInVertically(initialOffsetY = { it }, animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                shadowElevation = 16.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 24.dp)
                        .fillMaxWidth()
                ) {
                    // Header title & Promo code pill
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Choose a Ride",
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.ExtraBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Surface(
                            shape = CircleShape,
                            color = CabzoYellowContainer,
                            border = androidx.compose.foundation.BorderStroke(1.dp, CabzoYellow)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocalOffer,
                                    contentDescription = "Offer",
                                    tint = CabzoBlack,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "CABZO50 (50% OFF)",
                                    style = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = CabzoBlack)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Ride Options Vertical List
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .heightIn(max = 280.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(defaultRideOptions) { option ->
                            RideOptionCard(
                                rideOption = option,
                                isSelected = option.id == selectedRideOption.id,
                                onSelect = { selectedRideOption = option }
                            )
                        }
                    }

                    HorizontalDivider(
                        color = CabzoGrayBorder,
                        modifier = Modifier.padding(vertical = 14.dp, horizontal = 20.dp)
                    )

                    // Payment Method Selector
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    selectedPaymentMethod = if (selectedPaymentMethod.contains("Wallet")) "UPI / GPay" else "Cabzo Wallet (₹540)"
                                }
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountBalanceWallet,
                                contentDescription = "Payment",
                                tint = CabzoBlack,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = selectedPaymentMethod,
                                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Change Payment",
                                tint = CabzoGrayText,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Text(
                            text = "Fare: ₹${selectedRideOption.price.toInt()}",
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.ExtraBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Primary Book Button
                    Button(
                        onClick = {
                            bookingState = BookingState.SearchingDriver(
                                selectedRide = selectedRideOption,
                                pickupLocation = pickupLocation,
                                dropLocation = dropLocation
                            )
                        },
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CabzoYellow,
                            contentColor = CabzoBlack
                        )
                    ) {
                        Text(
                            text = "Book ${selectedRideOption.name}",
                            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                        )
                    }
                }
            }
        }

        // 4. Searching & Driver Matched Overlay
        if (bookingState is BookingState.SearchingDriver ||
            bookingState is BookingState.DriverMatched ||
            bookingState is BookingState.TripInProgress
        ) {
            DriverMatchingOverlay(
                bookingState = bookingState,
                onCancelBooking = { bookingState = BookingState.SelectingRide(pickupLocation, dropLocation) },
                onStartTrip = {
                    bookingState = BookingState.TripInProgress(
                        driver = sampleDriver,
                        selectedRide = selectedRideOption,
                        pickupLocation = pickupLocation,
                        dropLocation = dropLocation,
                        progress = 0.5f
                    )
                },
                onCompleteTrip = {
                    showTripCompletedDialog = true
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

        // 5. Search Destination Sheet
        if (isSearchSheetOpen) {
            SearchDestinationSheet(
                pickupLocation = pickupLocation,
                dropLocation = dropLocation,
                onDismiss = { isSearchSheetOpen = false },
                onConfirmLocations = { p, d ->
                    pickupLocation = p
                    dropLocation = d
                    isSearchSheetOpen = false
                    bookingState = BookingState.SelectingRide(p, d)
                }
            )
        }

        // 6. Trip Completed Modal
        if (showTripCompletedDialog) {
            TripCompletedDialog(
                driver = sampleDriver,
                fare = "₹${selectedRideOption.price.toInt()}",
                onDismiss = {
                    showTripCompletedDialog = false
                    bookingState = BookingState.Idle
                    dropLocation = ""
                },
                onSubmitRating = { _, _ ->
                    showTripCompletedDialog = false
                    bookingState = BookingState.Idle
                    dropLocation = ""
                }
            )
        }
    }
}
