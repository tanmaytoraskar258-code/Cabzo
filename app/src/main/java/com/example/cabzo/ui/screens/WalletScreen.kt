package com.example.cabzo.ui.screens

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cabzo.ui.theme.*

data class Transaction(
    val id: String,
    val title: String,
    val date: String,
    val amount: String,
    val isCredit: Boolean
)

val sampleTransactions = listOf(
    Transaction("1", "Added via GPay UPI", "Today, 10:30 AM", "+₹500.00", true),
    Transaction("2", "Paid for Ride CBZ-98211", "Today, 08:30 AM", "-₹749.00", false),
    Transaction("3", "Cashback Received (CABZO50)", "Yesterday", "+₹50.00", true),
    Transaction("4", "Paid for Ride CBZ-97420", "Yesterday, 06:15 PM", "-₹149.00", false)
)

@Composable
fun WalletScreen(
    modifier: Modifier = Modifier
) {
    var walletBalance by remember { mutableDoubleStateOf(540.0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Cabzo Wallet",
            style = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.ExtraBold),
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Balance Card
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = CabzoYellow,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(22.dp)
            ) {
                Text(
                    text = "TOTAL BALANCE",
                    style = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CabzoBlack.copy(alpha = 0.7f)),
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "₹${walletBalance.toInt()}.00",
                    style = TextStyle(fontSize = 36.sp, fontWeight = FontWeight.ExtraBold, color = CabzoBlack)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Quick Top-up",
                    style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Bold, color = CabzoBlack)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf(100, 200, 500, 1000).forEach { amount ->
                        Surface(
                            shape = CircleShape,
                            color = CabzoBlack,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { walletBalance += amount }
                        ) {
                            Box(
                                modifier = Modifier.padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "+₹$amount",
                                    style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = CabzoYellow)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Active Promo Offer
        Surface(
            shape = RoundedCornerShape(18.dp),
            color = CabzoBlack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(CabzoYellow),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalOffer,
                        contentDescription = "Promo",
                        tint = CabzoBlack,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "CABZO50 Applied",
                        style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = CabzoYellow)
                    )
                    Text(
                        text = "50% OFF up to ₹100 on your next 3 rides",
                        style = TextStyle(fontSize = 12.sp, color = CabzoOffWhite.copy(alpha = 0.8f))
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "TRANSACTION HISTORY",
            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold, color = CabzoGrayText),
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Transactions List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            items(sampleTransactions) { tx ->
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(if (tx.isCredit) CabzoGreen.copy(alpha = 0.15f) else CabzoGrayLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (tx.isCredit) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
                                contentDescription = tx.title,
                                tint = if (tx.isCredit) CabzoGreen else CabzoBlack,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = tx.title,
                                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = tx.date,
                                style = TextStyle(fontSize = 12.sp, color = CabzoGrayText)
                            )
                        }

                        Text(
                            text = tx.amount,
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (tx.isCredit) CabzoGreen else MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }
            }
        }
    }
}
