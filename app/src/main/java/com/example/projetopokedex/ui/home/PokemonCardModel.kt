package com.example.projetopokedex.ui.home

data class PokemonAttackUi(
    val name: String,
    val damage: Int?
)

data class PokemonCardUi(
    val name: String,
    val type: String,
    val hp: Int,
    val imageUrl: String,
    val attacks: List<PokemonAttackUi>,
    val cardNumber: Int
)
