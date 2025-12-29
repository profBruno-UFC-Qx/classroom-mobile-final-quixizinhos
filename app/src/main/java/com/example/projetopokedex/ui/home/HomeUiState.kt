package com.example.projetopokedex.ui.home

import com.example.projetopokedex.ui.home.PokemonCardUi

data class HomeUiState(
    val userName: String = "",
    val lastDrawCard: PokemonCardUi? = null, // carta recém-sorteada
    val canDrawToday: Boolean = true,
    val alreadyHasCard: Boolean = false,     // se o user já tinha a carta
    val errorMessage: String? = null,
    val qrContent: String? = null
)
