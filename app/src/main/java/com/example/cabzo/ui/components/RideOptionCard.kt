package com.example.cabzo.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cabzo.model.RideCategory
import com.example.cabzo.model.RideOption
import com.example.cabzo.ui.theme.*

@Composable
fun RideOptionCard(
    rideOption: RideOption,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.02f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "cardScale"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isSelected) CabzoYellow else Color.Transparent,
        label = "borderColor"
    )

    val containerColor by animateColorAsState(
        targetValue = if (isSelected) CabzoYellowContainer.copy(alpha = 0.6f) else MaterialTheme.colorScheme.surface,
        label = "containerColor"
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale)
            .clip(RoundedCornerShape(16.dp))
            .border(2.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable { onSelect() },
        color = containerColor,
        tonalElevation = if (isSelected) 4.dp else 1.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Vehicle Icon Box
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) CabzoYellow else CabzoGrayLight),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getRideIcon(rideOption.rideType),
                    contentDescription = rideOption.name,
                    tint = CabzoBlack,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Details Column
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = rideOption.name,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    // Capacity Badge
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Capacity",
                            modifier = Modifier.size(12.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "${rideOption.capacity}",
                            style = TextStyle(
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }

                    rideOption.badge?.let { badgeText ->
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = CircleShape,
                            color = CabzoBlack
                        ) {
                            Text(
                                text = badgeText,
                                style = TextStyle(
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = CabzoYellow
                                ),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "${rideOption.etaMinutes} mins away • ${rideOption.description}",
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Price Column
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "₹${rideOption.price.toInt()}",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )

                rideOption.originalPrice?.let { orig ->
                    Text(
                        text = "₹${orig.toInt()}",
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = CabzoGrayText,
                            textDecoration = TextDecoration.LineThrough
                        )
                    )
                }
            }
        }
    }
}

private fun getRideIcon(category: RideCategory): ImageVector {
    return when (category) {
        RideCategory.MINI -> Icons.Default.DirectionsCar
        RideCategory.SEDAN -> Icons.Default.LocalTaxi
        RideCategory.XL -> Icons.Default.AirportShuttle
        RideCategory.AUTO -> Icons.Default.ElectricRickshaw
        RideCategory.BIKE -> Icons.Default.TwoWheeler
        RideCategory.LUXURY -> Icons.Default.DirectionsCar
    }
}
