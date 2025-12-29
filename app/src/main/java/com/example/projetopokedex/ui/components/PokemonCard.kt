package com.example.projetopokedex.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.projetopokedex.R
import com.example.projetopokedex.ui.home.PokemonCardUi

@Composable
fun PokemonCard(
    data: PokemonCardUi,
    modifier: Modifier = Modifier,
    compact: Boolean = false
) {
    val nameFontSize = if (compact) 10.sp else 16.sp
    val typeFontSize = if (compact) 8.sp else 12.sp

    Box(
        modifier = modifier
            .width(if (compact) 120.dp else 200.dp)
            .height(if (compact) 160.dp else 280.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.fundo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(12.dp))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            // Topo: nome e tipo
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = data.name,
                    fontSize = nameFontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = data.type,
                    fontSize = typeFontSize,
                    textAlign = TextAlign.End,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Área da imagem do Pokémon
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .border(
                        width = 0.3.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(4.dp)
                    ),
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (data.imageUrl != null) {
                        AsyncImage(
                            model = data.imageUrl,
                            contentDescription = data.name,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Text(
                            text = "Sem imagem",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // HP
            Text(
                text = "${data.hp} Hp",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Ataques (0, 1 ou 2)
            data.attacks.take(2).forEach { attack ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = attack.name,
                        fontSize = 14.sp
                    )
                    if (attack.damage != null) {
                        Text(
                            text = attack.damage.toString(),
                            fontSize = 14.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.weight(1f))


            Spacer(modifier = Modifier.height(8.dp))

            // Número da carta
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Card(
                    shape = RoundedCornerShape(50),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = data.cardNumber.toString(),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}