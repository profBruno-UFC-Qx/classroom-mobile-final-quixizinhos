package com.example.projetopokedex.ui.home

data class HomeUiState(
    val userName: String = "",
    val lastDrawCard: PokemonCardUi? = null,
    val canDrawToday: Boolean = true,
    val alreadyHasCard: Boolean = false,
    val errorMessage: String? = null,
    val qrContent: String? = null,
    val isDrawing: Boolean = false
)
