package com.example.projetopokedex.ui.scanner

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScannerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ScannerUiState())
    val uiState: StateFlow<ScannerUiState> = _uiState.asStateFlow()

    fun onScanClick() {
        _uiState.value = _uiState.value.copy(isScanning = true)
    }

    fun onScanComplete(code: String) {
        _uiState.value = _uiState.value.copy(
            isScanning = false,
            scannedCode = code
        )
    }

    fun onScanError(error: String) {
        _uiState.value = _uiState.value.copy(
            isScanning = false,
            errorMessage = error
        )
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}

