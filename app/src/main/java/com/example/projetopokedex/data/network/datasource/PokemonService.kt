package com.example.projetopokedex.data.network.datasource

import com.example.projetopokedex.data.network.model.PokeApiPokemon
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {
    @GET("pokemon/{idOrName}")
    suspend fun getPokemon(
        @Path("idOrName") idOrName: String
    ): PokeApiPokemon
}