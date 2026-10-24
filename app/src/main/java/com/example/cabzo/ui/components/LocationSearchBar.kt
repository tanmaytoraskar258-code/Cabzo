package com.example.cabzo.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cabzo.ui.theme.*

data class SavedPlace(
    val title: String,
    val subtitle: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

val defaultSavedPlaces = listOf(
    SavedPlace("Kempegowda Int'l Airport T2", "Devanahalli, Bengaluru", Icons.Default.FlightTakeoff),
    SavedPlace("Work", "Tech Park Tower B, Outer Ring Rd", Icons.Default.Work),
    SavedPlace("Home", "12th Main Road, Indiranagar", Icons.Default.Home),
    SavedPlace("Phoenix Marketcity", "Whitefield Main Rd, Mahadevapura", Icons.Default.ShoppingBag),
    SavedPlace("MG Road Metro Station", "MG Road, Shanthala Nagar", Icons.Default.Train)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationSearchBar(
    pickupLocation: String,
    dropLocation: String,
    onDestinationSelected: (String) -> Unit,
    onOpenSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable { onOpenSearch() }
            .shadow(8.dp, RoundedCornerShape(24.dp)),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Yellow Search Circle
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(CabzoYellow),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = CabzoBlack,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = if (dropLocation.isEmpty()) "Where to?" else dropLocation,
                    style = TextStyle(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(CabzoGreen)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = pickupLocation,
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        maxLines = 1
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Surface(
                shape = CircleShape,
                color = CabzoGrayLight,
                modifier = Modifier.size(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "Ride later",
                        tint = CabzoBlack,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchDestinationSheet(
    pickupLocation: String,
    dropLocation: String,
    onDismiss: () -> Unit,
    onConfirmLocations: (pickup: String, drop: String) -> Unit
) {
    var pickupText by remember { mutableStateOf(pickupLocation) }
    var dropText by remember { mutableStateOf(dropLocation) }
    var isSwapped by remember { mutableStateOf(false) }

    val swapRotation by animateFloatAsState(
        targetValue = if (isSwapped) 180f else 0f,
        animationSpec = tween(400),
        label = "swapRotation"
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = {
            BottomSheetDefaults.DragHandle()
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Select Route",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.ExtraBold),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Pickup & Drop input box
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = CabzoGrayLight,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left Dots & Line
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(CabzoGreen)
                        )
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(28.dp)
                                .background(CabzoGrayText.copy(alpha = 0.4f))
                        )
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(CabzoBlack)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Input fields
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = pickupText,
                            onValueChange = { pickupText = it },
                            placeholder = { Text("Enter Pickup Location") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            textStyle = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        )

                        HorizontalDivider(
                            color = CabzoGrayBorder,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )

                        OutlinedTextField(
                            value = dropText,
                            onValueChange = { dropText = it },
                            placeholder = { Text("Where are you going?") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            textStyle = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Swap Button
                    IconButton(
                        onClick = {
                            isSwapped = !isSwapped
                            val temp = pickupText
                            pickupText = dropText
                            dropText = temp
                        },
                        modifier = Modifier
                            .rotate(swapRotation)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(CabzoYellow)
                    ) {
                        Icon(
                            imageVector = Icons.Default.SwapVert,
                            contentDescription = "Swap Locations",
                            tint = CabzoBlack,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "SAVED & RECENT PLACES",
                style = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CabzoGrayText),
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Saved places list
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.heightIn(max = 240.dp)
            ) {
                items(defaultSavedPlaces) { place ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                dropText = place.title
                                onConfirmLocations(pickupText, place.title)
                            }
                            .padding(vertical = 10.dp, horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(CabzoYellowContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = place.icon,
                                contentDescription = place.title,
                                tint = CabzoBlack,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = place.title,
                                style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = place.subtitle,
                                style = TextStyle(fontSize = 12.sp, color = CabzoGrayText),
                                maxLines = 1
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Select",
                            tint = CabzoGrayText,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (dropText.isNotBlank()) {
                        onConfirmLocations(pickupText, dropText)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CabzoYellow,
                    contentColor = CabzoBlack
                )
            ) {
                Text(
                    text = "Confirm Destination",
                    style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.ExtraBold)
                )
            }
        }
    }
}
