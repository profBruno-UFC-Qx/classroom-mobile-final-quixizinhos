package com.example.projetopokedex.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projetopokedex.R

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onLogoutClick: () -> Unit,
    onSortearClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 24.dp, bottom = 12.dp)
        ) {
            // HEADER
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "DexGo",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = uiState.userName.ifBlank { "Usuário" },
                        fontSize = 14.sp
                    )

                    IconButton(onClick = onLogoutClick) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Sair"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // CARD DE TEXTO
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.colete_novas_cartas_todos_os_dias_atrav_s_de_sorteios_aleat_rios),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.cada_carta_cont_m_um_qrcode_escaneie_cartas_de_outros_jogadores_para_conquistar_novas_cartas),
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(100.dp))

            // IMAGEM CENTRAL
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image_sorteio_diario),
                    contentDescription = "Cartas",
                    modifier = Modifier.size(220.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable(enabled = uiState.canDrawToday && !uiState.isDrawing, onClick = onSortearClick),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (uiState.isDrawing) "Sorteando..."
                               else if (uiState.canDrawToday) "Sortear"
                               else "Amanhã tem mais!",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
            uiState.errorMessage?.let { msg ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = msg,
                    fontSize = 12.sp,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            if (!uiState.canDrawToday && uiState.errorMessage == null && !uiState.isDrawing) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Você já realizou o sorteio de hoje. Volte amanhã.",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}