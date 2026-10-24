package com.example.cabzo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.cabzo.ui.components.CabzoBottomNav
import com.example.cabzo.ui.components.CabzoTab
import com.example.cabzo.ui.screens.*
import com.example.cabzo.ui.theme.CabzoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkMode by remember { mutableStateOf(false) }
            var showSplash by remember { mutableStateOf(true) }
            var selectedTab by remember { mutableStateOf(CabzoTab.HOME) }

            CabzoTheme(darkTheme = isDarkMode) {
                if (showSplash) {
                    SplashScreen(
                        onSplashFinished = { showSplash = false }
                    )
                } else {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            CabzoBottomNav(
                                selectedTab = selectedTab,
                                onTabSelected = { selectedTab = it }
                            )
                        }
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            Crossfade(
                                targetState = selectedTab,
                                animationSpec = tween(300),
                                label = "tabCrossfade"
                            ) { tab ->
                                when (tab) {
                                    CabzoTab.HOME -> HomeScreen()
                                    CabzoTab.ACTIVITY -> ActivityScreen(
                                        onRebookRide = {
                                            selectedTab = CabzoTab.HOME
                                        }
                                    )
                                    CabzoTab.WALLET -> WalletScreen()
                                    CabzoTab.PROFILE -> ProfileScreen(
                                        isDarkMode = isDarkMode,
                                        onToggleDarkMode = { isDarkMode = it }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
