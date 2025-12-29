package com.example.projetopokedex.data.network.model

data class PokeApiPokemon(
    val id: Int,
    val name: String,
    val types: List<PokeTypeSlot>,
    val stats: List<PokeStat>,
    val sprites: PokeSprites,
    val moves: List<PokeMoveSlot>
)

data class PokeTypeSlot(
    val slot: Int,
    val type: NamedApiResource
)

data class PokeStat(
    val base_stat: Int,
    val stat: NamedApiResource
)

data class PokeSprites(
    val front_default: String?
)

data class PokeMoveSlot(
    val move: NamedApiResource
)

data class NamedApiResource(
    val name: String,
    val url: String
)