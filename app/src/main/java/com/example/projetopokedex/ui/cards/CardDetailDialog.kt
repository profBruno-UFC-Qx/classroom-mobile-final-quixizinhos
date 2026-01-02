package com.example.projetopokedex.ui.cards

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.projetopokedex.ui.components.PokemonCard
import com.example.projetopokedex.ui.components.PokemonCardBack

@Composable
fun CardDetailDialog(
    card: CollectionCardUi,
    isShowingBack: Boolean,
    onToggleFace: () -> Unit,
    onDismiss: () -> Unit,
    qrBitmap: Bitmap? = null
) {
    Box(
        modifier = Modifier
            .clickable(onClick = onDismiss)
    ) {
        Card(
            modifier = Modifier
                .width(260.dp)
                .height(360.dp)
                .clickable(onClick = onToggleFace),
            shape = CardDefaults.shape,
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            if (isShowingBack) {
                PokemonCardBack(
                    modifier = Modifier.fillMaxSize(),
                    qrBitmap = qrBitmap
                )
            } else {
                PokemonCard(
                    data = card.card,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}