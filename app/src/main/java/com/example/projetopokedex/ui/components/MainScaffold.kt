package com.example.projetopokedex.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.projetopokedex.ui.navigation.HomeTab

@Composable
fun MainScaffold(
    selectedTab: HomeTab,
    onTabSelected: (HomeTab) -> Unit,
    overlayContent: (@Composable () -> Unit)? = null,
    onOverlayDismiss: (() -> Unit)? = null,
    backgroundColor: Color = Color.White,
    content: @Composable () -> Unit
) {
    Scaffold(
        containerColor = backgroundColor,
        bottomBar = {
            HomeBottomBar(
                selectedTab = selectedTab,
                onTabSelected = onTabSelected
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                content()
            }

            overlayContent?.let { overlay ->
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.6f))
                        .clickable(enabled = onOverlayDismiss != null) {
                            onOverlayDismiss?.invoke()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    overlay()
                }
            }
        }
    }
}