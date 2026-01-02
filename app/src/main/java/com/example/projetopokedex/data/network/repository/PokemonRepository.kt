package com.example.projetopokedex.data.network.repository

import com.example.projetopokedex.data.network.datasource.PokemonService
import com.example.projetopokedex.data.network.model.PokeApiPokemon
import com.example.projetopokedex.ui.home.PokemonAttackUi
import com.example.projetopokedex.ui.home.PokemonCardUi
import retrofit2.HttpException

class PokemonRepository(
    private val api: PokemonService
) {
    suspend fun getPokemonCard(idOrName: String, cardNumber: Int): Result<PokemonCardUi> = try {
        val apiModel = api.getPokemon(idOrName)
        Result.success(apiModel.toCardUi(cardNumber))
    } catch (e: HttpException) {
        Result.failure(e)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

private fun PokeApiPokemon.toCardUi(cardNumber: Int): PokemonCardUi {
    val hp = stats.firstOrNull { it.stat.name == "hp" }?.base_stat ?: 50
    val mainType = types.firstOrNull()?.type?.name ?: "normal"

    val attacks = moves.take(2).map { slot ->
        PokemonAttackUi(
            name = slot.move.name.replace("-", " ").replaceFirstChar { it.uppercase() },
            damage = (20..80).random()
        )
    }

    return PokemonCardUi(
        name = name.replaceFirstChar { it.uppercase() },
        type = mainType.replaceFirstChar { it.uppercase() },
        hp = hp,
        imageUrl = sprites.front_default,
        attacks = attacks,
        cardNumber = cardNumber
    )
}

