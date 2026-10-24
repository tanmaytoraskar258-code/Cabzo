package com.example.cabzo.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cabzo.model.BookingState
import com.example.cabzo.model.DriverDetails
import com.example.cabzo.ui.theme.*

@Composable
fun DriverMatchingOverlay(
    bookingState: BookingState,
    onCancelBooking: () -> Unit,
    onStartTrip: () -> Unit,
    onCompleteTrip: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp,
        shadowElevation = 16.dp
    ) {
        AnimatedContent(
            targetState = bookingState,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) + slideInVertically { it / 2 } togetherWith
                        fadeOut(animationSpec = tween(200)) + slideOutVertically { it / 2 }
            },
            label = "bookingStateTransition"
        ) { state ->
            when (state) {
                is BookingState.SearchingDriver -> {
                    SearchingDriverView(onCancel = onCancelBooking)
                }

                is BookingState.DriverMatched -> {
                    DriverAssignedView(
                        driver = state.driver,
                        onStartTrip = onStartTrip
                    )
                }

                is BookingState.TripInProgress -> {
                    TripInProgressView(
                        driver = state.driver,
                        progress = state.progress,
                        onCompleteTrip = onCompleteTrip
                    )
                }

                else -> Box {}
            }
        }
    }
}

@Composable
private fun SearchingDriverView(
    onCancel: () -> Unit
) {
    var step by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(400)
            step = (step + 1) % 3
        }
    }
    val dots = ".".repeat(step + 1)

    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(CircleShape),
            color = CabzoYellow,
            trackColor = CabzoYellowContainer
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Connecting with nearby drivers$dots",
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.ExtraBold),
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Matching you with top-rated Cabzo drivers in Indiranagar",
            style = TextStyle(fontSize = 13.sp, color = CabzoGrayText),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        OutlinedButton(
            onClick = onCancel,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = CabzoRed
            ),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, CabzoRed)
        ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Cancel")
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Cancel Search",
                style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Composable
private fun DriverAssignedView(
    driver: DriverDetails,
    onStartTrip: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
    ) {
        // ETA & OTP Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Driver Arriving",
                    style = TextStyle(fontSize = 13.sp, color = CabzoGrayText, fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "${driver.etaMinutes} mins away",
                    style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = CabzoGreen)
                )
            }

            // OTP Chip
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = CabzoYellowContainer,
                border = androidx.compose.foundation.BorderStroke(1.5.dp, CabzoYellow)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "OTP: ",
                        style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Bold, color = CabzoBlack)
                    )
                    Text(
                        text = driver.otp,
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = CabzoBlack)
                    )
                }
            }
        }

        HorizontalDivider(
            color = CabzoGrayBorder,
            modifier = Modifier.padding(vertical = 18.dp)
        )

        // Driver Info Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Driver Avatar
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(CabzoYellow),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = driver.name,
                    tint = CabzoBlack,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = driver.name,
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = CabzoYellowDark,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${driver.rating} • ${driver.totalRides} rides",
                        style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.SemiBold),
                        color = CabzoGrayText
                    )
                }
            }

            // Vehicle Plate Number
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = CabzoYellow,
                    border = androidx.compose.foundation.BorderStroke(1.dp, CabzoBlack)
                ) {
                    Text(
                        text = driver.vehicleNumber,
                        style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = CabzoBlack),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = driver.vehicleModel,
                    style = TextStyle(fontSize = 12.sp, color = CabzoGrayText)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Action Buttons: Call, Message, Start Trip Simulation
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconButton(
                onClick = { /* Call driver */ },
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(CabzoGrayLight)
            ) {
                Icon(imageVector = Icons.Default.Call, contentDescription = "Call", tint = CabzoBlack)
            }

            IconButton(
                onClick = { /* Message driver */ },
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(CabzoGrayLight)
            ) {
                Icon(imageVector = Icons.Default.Message, contentDescription = "Message", tint = CabzoBlack)
            }

            Button(
                onClick = onStartTrip,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CabzoYellow,
                    contentColor = CabzoBlack
                )
            ) {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Start Trip")
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Start Ride",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
                )
            }
        }
    }
}

@Composable
private fun TripInProgressView(
    driver: DriverDetails,
    progress: Float,
    onCompleteTrip: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(CabzoGreen)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "On the way to Kempegowda Airport T2",
                    style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.ExtraBold),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Surface(
                shape = CircleShape,
                color = CabzoRed.copy(alpha = 0.15f)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = "Safety SOS",
                        tint = CabzoRed,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Safety",
                        style = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CabzoRed)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Progress Bar
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(CircleShape),
            color = CabzoYellow,
            trackColor = CabzoGrayLight
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Driver: ${driver.name}",
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
                )
                Text(
                    text = driver.vehicleNumber,
                    style = TextStyle(fontSize = 12.sp, color = CabzoGrayText)
                )
            }

            Button(
                onClick = onCompleteTrip,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CabzoBlack,
                    contentColor = CabzoYellow
                )
            ) {
                Text(
                    text = "Arrive & Pay",
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}
