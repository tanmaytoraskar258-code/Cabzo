package com.example.cabzo.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cabzo.model.DriverDetails
import com.example.cabzo.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripCompletedDialog(
    driver: DriverDetails,
    fare: String,
    onDismiss: () -> Unit,
    onSubmitRating: (rating: Int, tipAmount: Int) -> Unit
) {
    var rating by remember { mutableIntStateOf(5) }
    var selectedTip by remember { mutableIntStateOf(20) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Checkmark Icon
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(CabzoYellow),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Completed",
                    tint = CabzoBlack,
                    modifier = Modifier.size(38.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "You've Arrived!",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.ExtraBold),
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "Hope you enjoyed your ride with ${driver.name}",
                style = TextStyle(fontSize = 14.sp, color = CabzoGrayText)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Fare Box
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = CabzoGrayLight,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Total Fare",
                            style = TextStyle(fontSize = 13.sp, color = CabzoGrayText)
                        )
                        Text(
                            text = fare,
                            style = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = CabzoBlack)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = CabzoYellow
                    ) {
                        Text(
                            text = "PAID VIA WALLET",
                            style = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = CabzoBlack),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Rating Star Bar
            Text(
                text = "Rate your ride",
                style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (star in 1..5) {
                    val isStarred = star <= rating
                    val starScale by animateFloatAsState(
                        targetValue = if (isStarred) 1.2f else 1.0f,
                        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                        label = "starScale"
                    )

                    IconButton(
                        onClick = { rating = star },
                        modifier = Modifier.scale(starScale)
                    ) {
                        Icon(
                            imageVector = if (isStarred) Icons.Default.Star else Icons.Outlined.StarBorder,
                            contentDescription = "$star Stars",
                            tint = if (isStarred) CabzoYellowDark else CabzoGrayText,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Tip Selection Chips
            Text(
                text = "Add a tip for ${driver.name}",
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = CabzoGrayText)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                listOf(0, 20, 50, 100).forEach { amount ->
                    val isSelected = selectedTip == amount
                    val chipBg = if (isSelected) CabzoYellow else CabzoGrayLight
                    val chipText = if (amount == 0) "No Tip" else "+₹$amount"

                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { selectedTip = amount },
                        color = chipBg
                    ) {
                        Box(
                            modifier = Modifier.padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = chipText,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                                    color = CabzoBlack
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = { onSubmitRating(rating, selectedTip) },
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
                    text = "Done",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                )
            }
        }
    }
}
