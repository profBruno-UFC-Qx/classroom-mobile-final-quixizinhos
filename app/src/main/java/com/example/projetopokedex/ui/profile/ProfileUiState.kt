package com.example.projetopokedex.ui.profile

data class ProfileUiState(
    val avatar: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isEditing: Boolean = false,
    val showSuccessMessage: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val isDarkIcon: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
