package com.example.cabzo.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cabzo.model.BookingState
import com.example.cabzo.ui.theme.*
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CabzoMap(
    bookingState: BookingState,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()
    val textMeasurer = rememberTextMeasurer()

    // Animation 1: Continuous movement phase for animated cabs
    val infiniteTransition = rememberInfiniteTransition(label = "mapAnimations")
    val animTime by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "cabMovement"
    )

    // Animation 2: Radar pulse ripple for SearchingDriver
    val radarPulse by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "radarPulse"
    )

    // Animation 3: Route line dash phase
    val dashPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 40f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "dashPhase"
    )

    // Colors based on theme
    val mapBgColor = if (isDark) Color(0xFF181C24) else Color(0xFFF3F4F6)
    val roadColor = if (isDark) Color(0xFF232936) else Color(0xFFFFFFFF)
    val mainRoadColor = if (isDark) Color(0xFF2C3545) else Color(0xFFE5E7EB)
    val buildingColor = if (isDark) Color(0xFF1E2430) else Color(0xFFEAEAEC)
    val parkColor = if (isDark) Color(0xFF1B2A24) else Color(0xFFE6F4EA)
    val waterColor = if (isDark) Color(0xFF172838) else Color(0xFFE3F2FD)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(mapBgColor)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // --- 1. Draw City Parks / Water Blocks ---
            drawRoundRect(
                color = parkColor,
                topLeft = Offset(width * 0.05f, height * 0.1f),
                size = Size(width * 0.35f, height * 0.2f),
                cornerRadius = CornerRadius(16.dp.toPx())
            )
            drawRoundRect(
                color = waterColor,
                topLeft = Offset(width * 0.6f, height * 0.05f),
                size = Size(width * 0.35f, height * 0.15f),
                cornerRadius = CornerRadius(16.dp.toPx())
            )
            drawRoundRect(
                color = parkColor,
                topLeft = Offset(width * 0.55f, height * 0.65f),
                size = Size(width * 0.4f, height * 0.25f),
                cornerRadius = CornerRadius(16.dp.toPx())
            )

            // --- 2. Draw Simulated City Grid / Buildings ---
            val buildingRects = listOf(
                Offset(width * 0.1f, height * 0.35f) to Size(width * 0.25f, height * 0.15f),
                Offset(width * 0.42f, height * 0.28f) to Size(width * 0.2f, height * 0.12f),
                Offset(width * 0.1f, height * 0.55f) to Size(width * 0.25f, height * 0.2f),
                Offset(width * 0.7f, height * 0.25f) to Size(width * 0.25f, height * 0.3f),
            )
            buildingRects.forEach { (topLeft, size) ->
                drawRoundRect(
                    color = buildingColor,
                    topLeft = topLeft,
                    size = size,
                    cornerRadius = CornerRadius(12.dp.toPx())
                )
            }

            // --- 3. Draw Roads (Network) ---
            val roadWidth = 24.dp.toPx()
            val mainRoadWidth = 36.dp.toPx()

            // Horizontal Roads
            drawLine(roadColor, Offset(0f, height * 0.3f), Offset(width, height * 0.3f), strokeWidth = mainRoadWidth)
            drawLine(roadColor, Offset(0f, height * 0.52f), Offset(width, height * 0.52f), strokeWidth = roadWidth)
            drawLine(roadColor, Offset(0f, height * 0.78f), Offset(width, height * 0.78f), strokeWidth = mainRoadWidth)

            // Vertical Roads
            drawLine(roadColor, Offset(width * 0.38f, 0f), Offset(width * 0.38f, height), strokeWidth = mainRoadWidth)
            drawLine(roadColor, Offset(width * 0.65f, 0f), Offset(width * 0.65f, height), strokeWidth = roadWidth)

            // Diagonal Main Highway
            val highwayPath = Path().apply {
                moveTo(0f, height * 0.2f)
                cubicTo(
                    width * 0.3f, height * 0.25f,
                    width * 0.5f, height * 0.6f,
                    width, height * 0.7f
                )
            }
            drawPath(
                path = highwayPath,
                color = mainRoadColor,
                style = Stroke(width = mainRoadWidth, cap = StrokeCap.Round)
            )

            // --- 4. Pickup & Drop Coordinates ---
            val pickupPos = Offset(width * 0.38f, height * 0.52f)
            val dropPos = Offset(width * 0.75f, height * 0.22f)

            // --- 5. Draw Animated Route Line if booking or active ---
            val showRoute = bookingState !is BookingState.Idle && bookingState !is BookingState.SelectingDestination
            if (showRoute) {
                val routePath = Path().apply {
                    moveTo(pickupPos.x, pickupPos.y)
                    cubicTo(
                        width * 0.45f, height * 0.4f,
                        width * 0.6f, height * 0.3f,
                        dropPos.x, dropPos.y
                    )
                }

                // Shadow path
                drawPath(
                    path = routePath,
                    color = CabzoYellow.copy(alpha = 0.3f),
                    style = Stroke(width = 16.dp.toPx(), cap = StrokeCap.Round)
                )

                // Animated dashed line
                drawPath(
                    path = routePath,
                    color = CabzoBlack,
                    style = Stroke(
                        width = 8.dp.toPx(),
                        cap = StrokeCap.Round,
                        pathEffect = PathEffect.dashPathEffect(
                            floatArrayOf(24f, 16f),
                            phase = -dashPhase
                        )
                    )
                )

                // Drop-off Pin Animation & Marker
                drawCircle(
                    color = CabzoBlack,
                    radius = 16.dp.toPx(),
                    center = dropPos
                )
                drawCircle(
                    color = CabzoYellow,
                    radius = 8.dp.toPx(),
                    center = dropPos
                )

                // Drop location label badge
                val dropText = "Destination T2"
                val textResult = textMeasurer.measure(
                    text = dropText,
                    style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                )
                val badgePadding = 8.dp.toPx()
                val badgeWidth = textResult.size.width + badgePadding * 2
                val badgeHeight = textResult.size.height + badgePadding

                drawRoundRect(
                    color = CabzoBlack,
                    topLeft = Offset(dropPos.x - badgeWidth / 2, dropPos.y - 48.dp.toPx()),
                    size = Size(badgeWidth, badgeHeight),
                    cornerRadius = CornerRadius(8.dp.toPx())
                )
                drawText(
                    textLayoutResult = textResult,
                    topLeft = Offset(
                        dropPos.x - textResult.size.width / 2,
                        dropPos.y - 48.dp.toPx() + badgePadding / 2
                    )
                )
            }

            // --- 6. Radar Pulse Animation (When Searching Driver) ---
            if (bookingState is BookingState.SearchingDriver) {
                val maxRadius = 140.dp.toPx()
                val currentRadius = maxRadius * radarPulse
                val alpha = (1f - radarPulse).coerceIn(0f, 1f)

                drawCircle(
                    color = CabzoYellow.copy(alpha = alpha * 0.4f),
                    radius = currentRadius,
                    center = pickupPos
                )
                drawCircle(
                    color = CabzoYellow,
                    radius = currentRadius,
                    center = pickupPos,
                    style = Stroke(width = 3.dp.toPx())
                )
            }

            // --- 7. Pickup Pin (Your Location) ---
            // Outer glowing ring
            drawCircle(
                color = CabzoYellow.copy(alpha = 0.25f),
                radius = 28.dp.toPx(),
                center = pickupPos
            )
            // Yellow pin base
            drawCircle(
                color = CabzoYellow,
                radius = 18.dp.toPx(),
                center = pickupPos
            )
            // Inner core
            drawCircle(
                color = CabzoBlack,
                radius = 7.dp.toPx(),
                center = pickupPos
            )

            // Pickup Label Badge
            val pickupText = "Pickup Here"
            val pickupTextResult = textMeasurer.measure(
                text = pickupText,
                style = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = CabzoBlack)
            )
            val pBadgeWidth = pickupTextResult.size.width + 16.dp.toPx()
            val pBadgeHeight = pickupTextResult.size.height + 8.dp.toPx()

            drawRoundRect(
                color = CabzoYellow,
                topLeft = Offset(pickupPos.x - pBadgeWidth / 2, pickupPos.y + 24.dp.toPx()),
                size = Size(pBadgeWidth, pBadgeHeight),
                cornerRadius = CornerRadius(20.dp.toPx())
            )
            drawText(
                textLayoutResult = pickupTextResult,
                topLeft = Offset(
                    pickupPos.x - pickupTextResult.size.width / 2,
                    pickupPos.y + 28.dp.toPx()
                )
            )

            // --- 8. Animated Nearby Cabs on Map ---
            // Draw 5 moving cabs in real-time
            val cabOffsets = listOf(
                Offset(
                    pickupPos.x + 120.dp.toPx() * cos(animTime.toDouble()).toFloat(),
                    pickupPos.y + 90.dp.toPx() * sin(animTime.toDouble()).toFloat()
                ) to (animTime * 57.3f + 90f),
                Offset(
                    pickupPos.x - 140.dp.toPx() * cos((animTime * 0.8f).toDouble()).toFloat(),
                    pickupPos.y + 110.dp.toPx() * sin((animTime * 0.8f).toDouble()).toFloat()
                ) to (-animTime * 45f),
                Offset(
                    pickupPos.x + 80.dp.toPx() * sin((animTime * 1.2f).toDouble()).toFloat(),
                    pickupPos.y - 130.dp.toPx() * cos((animTime * 1.2f).toDouble()).toFloat()
                ) to (animTime * 60f + 180f),
                Offset(
                    pickupPos.x - 90.dp.toPx(),
                    pickupPos.y - 80.dp.toPx() + 30.dp.toPx() * sin((animTime * 2f).toDouble()).toFloat()
                ) to 0f,
                Offset(
                    width * 0.72f + 40.dp.toPx() * cos(animTime.toDouble()).toFloat(),
                    height * 0.28f + 40.dp.toPx() * sin(animTime.toDouble()).toFloat()
                ) to 45f
            )

            cabOffsets.forEach { (pos, angle) ->
                rotate(degrees = angle, pivot = pos) {
                    // Cab shadow
                    drawRoundRect(
                        color = Color.Black.copy(alpha = 0.2f),
                        topLeft = Offset(pos.x - 11.dp.toPx(), pos.y - 19.dp.toPx()),
                        size = Size(22.dp.toPx(), 38.dp.toPx()),
                        cornerRadius = CornerRadius(6.dp.toPx())
                    )
                    // Cab yellow body
                    drawRoundRect(
                        color = CabzoYellow,
                        topLeft = Offset(pos.x - 10.dp.toPx(), pos.y - 18.dp.toPx()),
                        size = Size(20.dp.toPx(), 36.dp.toPx()),
                        cornerRadius = CornerRadius(5.dp.toPx())
                    )
                    // Roof top / Windshield
                    drawRoundRect(
                        color = CabzoBlack,
                        topLeft = Offset(pos.x - 7.dp.toPx(), pos.y - 10.dp.toPx()),
                        size = Size(14.dp.toPx(), 20.dp.toPx()),
                        cornerRadius = CornerRadius(3.dp.toPx())
                    )
                    // Roof yellow stripe
                    drawRect(
                        color = CabzoYellow,
                        topLeft = Offset(pos.x - 4.dp.toPx(), pos.y - 4.dp.toPx()),
                        size = Size(8.dp.toPx(), 8.dp.toPx())
                    )
                }
            }
        }

        // --- 9. Re-center Location Button ---
        FloatingActionButton(
            onClick = { /* Recenter map */ },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 64.dp, end = 16.dp)
                .size(44.dp)
                .shadow(6.dp, CircleShape),
            containerColor = CabzoWhite,
            contentColor = CabzoBlack
        ) {
            Icon(
                imageVector = Icons.Default.MyLocation,
                contentDescription = "My Location",
                tint = CabzoBlack
            )
        }
    }
}
