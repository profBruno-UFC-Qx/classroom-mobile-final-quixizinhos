package com.example.projetopokedex.ui.cards

import androidx.lifecycle.ViewModel
import com.example.projetopokedex.ui.home.PokemonAttackUi
import com.example.projetopokedex.ui.home.PokemonCardUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CardsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        CardsUiState(
            cards = fakeCards() // temporário
        )
    )
    val uiState: StateFlow<CardsUiState> = _uiState.asStateFlow()

    fun onCardClick(card: CollectionCardUi) {
        _uiState.value = _uiState.value.copy(
            selectedCard = card,
            isShowingBack = false
        )
    }

    fun onToggleFace() {
        _uiState.value = _uiState.value.copy(
            isShowingBack = !_uiState.value.isShowingBack
        )
    }

    fun onDismissDialog() {
        _uiState.value = _uiState.value.copy(
            selectedCard = null,
            isShowingBack = false
        )
    }

    private fun fakeCards(): List<CollectionCardUi> {
        val base = PokemonCardUi(
            name = "Pikachu",
            type = "Elétrico",
            hp = 200,
            imageUrl = "",
            attacks = listOf(
                PokemonAttackUi("Choque do trovão", 30),
                PokemonAttackUi("Esquivar", null)
            ),
            cardNumber = 7
        )
        return List(8) { index ->
            CollectionCardUi(
                id = index,
                card = base.copy(cardNumber = 7 + index)
            )
        }
    }
}