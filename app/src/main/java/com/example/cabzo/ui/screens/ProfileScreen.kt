package com.example.cabzo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.cabzo.ui.theme.*

@Composable
fun ProfileScreen(
    isDarkMode: Boolean,
    onToggleDarkMode: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
            .padding(bottom = 100.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Profile & Settings",
            style = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.ExtraBold),
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Profile Header Card
        Surface(
            shape = RoundedCornerShape(22.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(CabzoYellow),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "User",
                        tint = CabzoBlack,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Tanmay",
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.ExtraBold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "+91 98765 43210",
                        style = TextStyle(fontSize = 13.sp, color = CabzoGrayText)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        shape = CircleShape,
                        color = CabzoYellowContainer
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Rating",
                                tint = CabzoYellowDark,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "4.98 Rider Rating",
                                style = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CabzoBlack)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Safety Shield Banner
        Surface(
            shape = RoundedCornerShape(18.dp),
            color = CabzoYellowContainer,
            border = androidx.compose.foundation.BorderStroke(1.dp, CabzoYellow),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(CabzoYellow),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = "Safety",
                        tint = CabzoBlack,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Cabzo Safety Toolkit",
                        style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = CabzoBlack)
                    )
                    Text(
                        text = "Share ride status & emergency contacts 24/7",
                        style = TextStyle(fontSize = 12.sp, color = CabzoBlack.copy(alpha = 0.8f))
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "PREFERENCES",
            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold, color = CabzoGrayText),
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Settings items
        ProfileMenuItem(
            icon = Icons.Default.DarkMode,
            title = "Dark Theme",
            subtitle = "Sleek high-contrast dark layout",
            trailing = {
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = onToggleDarkMode,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = CabzoBlack,
                        checkedTrackColor = CabzoYellow
                    )
                )
            }
        )

        ProfileMenuItem(
            icon = Icons.Default.Home,
            title = "Saved Places",
            subtitle = "Home, Work, Airport T2"
        )

        ProfileMenuItem(
            icon = Icons.Default.Payment,
            title = "Payment Methods",
            subtitle = "Cabzo Wallet, UPI, Cards, Cash"
        )

        ProfileMenuItem(
            icon = Icons.Default.Help,
            title = "Help & Support",
            subtitle = "24/7 Live chat & past ride disputes"
        )

        ProfileMenuItem(
            icon = Icons.Default.Lock,
            title = "Privacy & Safety",
            subtitle = "Manage permissions & security OTP"
        )
    }
}

@Composable
private fun ProfileMenuItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    trailing: (@Composable () -> Unit)? = null
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(CabzoGrayLight),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = CabzoBlack,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    style = TextStyle(fontSize = 12.sp, color = CabzoGrayText)
                )
            }

            if (trailing != null) {
                trailing()
            } else {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Navigate",
                    tint = CabzoGrayText,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
