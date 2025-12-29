package com.example.projetopokedex.ui.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetopokedex.data.repository.CardsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CardsViewModel(
    private val cardsRepository: CardsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CardsUiState())
    val uiState: StateFlow<CardsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            cardsRepository.getUserCards().collect { cards ->
                _uiState.value = _uiState.value.copy(
                    cards = cards.mapIndexed { index, card ->
                        CollectionCardUi(id = index, card = card)
                    }
                )
            }
        }
    }

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

    fun drawDailyCard() {
        viewModelScope.launch {
            val result = cardsRepository.drawDailyCard()
            result.onFailure {
            }
        }
    }
}