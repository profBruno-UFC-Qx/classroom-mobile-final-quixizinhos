package com.example.projetopokedex.ui.theme

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

object ThemeManager {
    private var _isDarkTheme = mutableStateOf(false)
    val isDarkTheme: MutableState<Boolean> = _isDarkTheme

    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }

    fun setDarkTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
    }
}

