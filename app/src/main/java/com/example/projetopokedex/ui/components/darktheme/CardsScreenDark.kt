package com.example.projetopokedex.ui.components.darktheme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projetopokedex.ui.cards.CardsUiState
import com.example.projetopokedex.ui.cards.CollectionCardUi
import com.example.projetopokedex.ui.components.PokemonCard

@Composable
fun CardsScreenDark(
    state: CardsUiState,
    onCardClick: (CollectionCardUi) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 24.dp, bottom = 12.dp)
        ) {
            // Header
            Text(
                text = "DexGo",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Minhas Cartas",
                fontSize = 20.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (state.cards.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Você não possui nenhuma carta em seu baralho, realize os sorteios diários ou leia códigos QR de cartas de outros jogadores para expandir sua coleção!",
                        fontSize = 14.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.cards, key = { it.id }) { item ->
                        Box(
                            modifier = Modifier
                                .aspectRatio(0.72f)
                                .clickable { onCardClick(item) },
                            contentAlignment = Alignment.Center
                        ) {
                            PokemonCard(
                                data = item.card,
                                modifier = Modifier.fillMaxSize(),
                                compact = true
                            )
                        }
                    }
                }
            }
        }
    }
}

