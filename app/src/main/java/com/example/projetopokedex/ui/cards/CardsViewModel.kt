package com.example.projetopokedex.ui.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetopokedex.data.repository.CardsRepository
import com.example.projetopokedex.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CardsViewModel(
    private val cardsRepository: CardsRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CardsUiState())
    val uiState: StateFlow<CardsUiState> = _uiState.asStateFlow()

    init {
        observeLoginStateAndCards()
    }

    private fun observeLoginStateAndCards() {
        viewModelScope.launch {
            userRepository.isLoggedIn.collect { isLoggedIn ->
                if (isLoggedIn) {
                    observeUserCards()
                } else {
                    _uiState.value = _uiState.value.copy(cards = emptyList())
                }
            }
        }
    }

    private fun observeUserCards() {
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
}