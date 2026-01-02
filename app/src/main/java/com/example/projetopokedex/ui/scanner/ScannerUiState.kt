package com.example.projetopokedex.ui.scanner

data class ScannerUiState(
    val isScanning: Boolean = false,
    val scannedCode: String? = null,
    val errorMessage: String? = null
)

