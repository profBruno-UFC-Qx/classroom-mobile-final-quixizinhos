package com.example.projetopokedex.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projetopokedex.R

@Composable
fun PokemonCardBack(
    modifier: Modifier = Modifier,
    qrContent: @Composable () -> Unit = {
        // placeholder para o QRCode
        Box(
            modifier = Modifier
                .size(120.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "QR Code",
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
) {
    Box(
        modifier = modifier
            .width(200.dp)
            .height(280.dp)
    ) {
        // Fundo da cartinha
        Image(
            painter = painterResource(id = R.drawable.fundo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(12.dp))
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            // Pokébola topo esquerdo
            Image(
                painter = painterResource(id = R.drawable.pokebola),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopStart)
                    .offset(x = (-8).dp, y = (-8).dp)
            )

            // Pokébola inferior direito
            Image(
                painter = painterResource(id = R.drawable.pokebola),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = (8).dp, y = (8).dp)
            )

            // Área central para o QRCode
            Card(
                modifier = Modifier
                    .align(Alignment.Center),
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = androidx.compose.ui.graphics.Color.Gray
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .padding(2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    qrContent()
                }
            }
        }
    }
}
