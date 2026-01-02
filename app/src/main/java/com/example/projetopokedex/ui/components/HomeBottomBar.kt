package com.example.projetopokedex.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.projetopokedex.R
import com.example.projetopokedex.ui.navigation.HomeTab

@Composable
fun HomeBottomBar(
    selectedTab: HomeTab,
    onTabSelected: (HomeTab) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        Surface(
            color = Color.White,
            tonalElevation = 4.dp,
            shadowElevation = 8.dp,
            shape = RoundedCornerShape(24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomBarItem(
                    isSelected = selectedTab == HomeTab.Cards,
                    iconRes = R.drawable.icon_cards_pokemon,
                    onClick = { onTabSelected(HomeTab.Cards) }
                )
                BottomBarItem(
                    isSelected = selectedTab == HomeTab.Home,
                    iconRes = R.drawable.home,
                    onClick = { onTabSelected(HomeTab.Home) }
                )
                BottomBarItem(
                    isSelected = selectedTab == HomeTab.Qr,
                    iconRes = R.drawable.qr_code,
                    onClick = { onTabSelected(HomeTab.Qr) }
                )
                BottomBarItem(
                    isSelected = selectedTab == HomeTab.Profile,
                    iconRes = R.drawable.user_male,
                    onClick = { onTabSelected(HomeTab.Profile) }
                )
            }
        }
    }
}

@Composable
private fun BottomBarItem(
    isSelected: Boolean,
    iconRes: Int,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .height(2.dp)
                .width(24.dp)
                .background(
                    color = if (isSelected) Color.Black else Color.Transparent
                )
        )
    }
}
