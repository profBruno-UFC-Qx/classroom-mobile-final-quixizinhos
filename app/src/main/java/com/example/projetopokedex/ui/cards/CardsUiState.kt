package com.example.projetopokedex.ui.cards

data class CardsUiState(
    val cards: List<CollectionCardUi> = emptyList(),
    val selectedCard: CollectionCardUi? = null,
    val isShowingBack: Boolean = false
)
