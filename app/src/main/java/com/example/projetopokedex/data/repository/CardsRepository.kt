package com.example.projetopokedex.data.repository

import com.example.projetopokedex.data.local.cards.UserCardDao
import com.example.projetopokedex.data.local.cards.UserCardEntity
import com.example.projetopokedex.data.network.repository.PokemonRepository
import com.example.projetopokedex.domain.randomKantoId
import com.example.projetopokedex.ui.home.PokemonAttackUi
import com.example.projetopokedex.ui.home.PokemonCardUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

data class DrawResult(
    val card: PokemonCardUi,
    val alreadyOwned: Boolean
)

class CardsRepository(
    private val userCardDao: UserCardDao,
    private val pokemonRepository: PokemonRepository,
    private val userRepository: UserRepository
) {

    fun getUserCards(): Flow<List<PokemonCardUi>> = flow {
        val user = userRepository.getCurrentUser()
        if (user == null) {
            emit(emptyList())
            return@flow
        }

        emitAll(
            userCardDao.getCardsForUser(user.email).map { entities ->
                entities.map { it.toUi() }
            }
        )
    }

    suspend fun drawDailyCard(): Result<DrawResult> {
        val user = userRepository.getCurrentUser()
            ?: return Result.failure(IllegalStateException("Nenhum usuário logado"))

        if (!userRepository.canDrawToday()) {
            return Result.failure(IllegalStateException("Você já realizou o sorteio de hoje."))
        }

        val pokemonId = randomKantoId()
        val cardNumber = pokemonId

        // verifica se já tem essa carta
        val existing = userCardDao.getCardsForUserOnce(user.email)
        val alreadyOwned = existing.any { it.pokemonId == pokemonId }

        val result = pokemonRepository.getPokemonCard(pokemonId.toString(), cardNumber)

        return result.map { ui ->
            val entity = ui.toEntity(userEmail = user.email, pokemonId = pokemonId)
            userCardDao.insert(entity)
            userRepository.markDrawToday()
            DrawResult(card = ui, alreadyOwned = alreadyOwned)
        }
    }
}

// mappers
fun PokemonCardUi.toEntity(userEmail: String, pokemonId: Int): UserCardEntity =
    UserCardEntity(
        userEmail = userEmail,
        pokemonId = pokemonId,
        pokemonName = name,
        type = type,
        hp = hp,
        attackName1 = attacks.getOrNull(0)?.name,
        attackPower1 = attacks.getOrNull(0)?.damage,
        attackName2 = attacks.getOrNull(1)?.name,
        attackPower2 = attacks.getOrNull(1)?.damage,
        imageUrl = imageUrl,
        cardNumber = cardNumber,
        createdAt = System.currentTimeMillis()
    )

fun UserCardEntity.toUi(): PokemonCardUi =
    PokemonCardUi(
        name = pokemonName,
        type = type,
        hp = hp,
        imageUrl = imageUrl,
        attacks = listOfNotNull(
            attackName1?.let { PokemonAttackUi(it, attackPower1) },
            attackName2?.let { PokemonAttackUi(it, attackPower2) }
        ),
        cardNumber = cardNumber
    )