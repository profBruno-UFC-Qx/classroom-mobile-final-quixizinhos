package com.example.projetopokedex.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetopokedex.data.repository.CardsRepository
import com.example.projetopokedex.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository: UserRepository,
    private val cardsRepository: CardsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        observeUserName()
        refreshCanDrawToday()
    }

    private fun observeUserName() {
        viewModelScope.launch {
            userRepository.userName.collect { name ->
                _uiState.value = _uiState.value.copy(
                    userName = name.orEmpty()
                )
            }
        }
    }

    private fun refreshCanDrawToday() {
        viewModelScope.launch {
            val canDraw = userRepository.canDrawToday()
            _uiState.value = _uiState.value.copy(
                canDrawToday = canDraw
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }

    fun drawCard() {
        viewModelScope.launch {
            try {
                if (!userRepository.canDrawToday()) {
                    _uiState.value = _uiState.value.copy(
                        canDrawToday = false,
                        errorMessage = "Você já realizou o sorteio de hoje. Volte amanhã.",
                        lastDrawCard = null,
                        alreadyHasCard = false
                    )
                    return@launch
                }

                val result = cardsRepository.drawDailyCard()

                result
                    .onSuccess { drawResult ->
                        val card = drawResult.card
                        val user = userRepository.getCurrentUser()
                        val qr = if (user != null) "dexgo:${user.email}:${card.cardNumber}" else null

                        _uiState.value = _uiState.value.copy(
                            lastDrawCard = card,
                            alreadyHasCard = drawResult.alreadyOwned,
                            canDrawToday = false,
                            errorMessage = null,   // só limpa em sucesso
                            qrContent = qr
                        )
                    }
                    .onFailure { e ->
                        _uiState.value = _uiState.value.copy(
                            errorMessage = "Erro no sorteio: ${e.message}",
                            lastDrawCard = null,
                            alreadyHasCard = false
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Exceção no sorteio: ${e.javaClass.simpleName} - ${e.message}",
                    lastDrawCard = null,
                    alreadyHasCard = false
                )
            }
        }
    }

    fun clearLastDraw() {
        _uiState.value = _uiState.value.copy(
            lastDrawCard = null,
            alreadyHasCard = false,
            qrContent = null
        )
    }
}