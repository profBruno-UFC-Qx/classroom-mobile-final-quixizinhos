package com.example.projetopokedex.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetopokedex.data.model.UserLocal
import com.example.projetopokedex.data.repository.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState(isLoading = true))
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            val user = userRepository.getCurrentUser()
            _uiState.value = if (user != null) {
                ProfileUiState(
                    avatar = user.avatar,
                    name = user.name,
                    email = user.email,
                    password = user.password,
                    isLoading = false
                )
            } else {
                ProfileUiState(isLoading = false)
            }
        }
    }

    fun onToggleEdit() {
        val current = _uiState.value
        _uiState.value = current.copy(
            isEditing = !current.isEditing,
            showSuccessMessage = false,
            error = null
        )
    }

    fun onAvatarChange(value: String) {
        if (_uiState.value.isEditing) {
            _uiState.value = _uiState.value.copy(avatar = value)
        }
    }

    fun onNameChange(value: String) {
        if (_uiState.value.isEditing) {
            _uiState.value = _uiState.value.copy(name = value)
        }
    }

    fun onPasswordChange(value: String) {
        if (_uiState.value.isEditing) {
            _uiState.value = _uiState.value.copy(password = value)
        }
    }

    fun onToggleThemeIcon() {
        _uiState.value = _uiState.value.copy(
            isDarkIcon = !_uiState.value.isDarkIcon
        )
    }

    fun onSaveChanges() {
        val state = _uiState.value
        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, error = null)
            val result = userRepository.updateUser(
                UserLocal(
                    avatar = state.avatar,
                    name = state.name,
                    email = state.email,
                    password = state.password
                )
            )

            if (result.isSuccess) {
                _uiState.value = state.copy(
                    isEditing = false,
                    showSuccessMessage = true,
                    isLoading = false
                )

                launch {
                    delay(2000)
                    _uiState.value = _uiState.value.copy(showSuccessMessage = false)
                }

            } else {
                _uiState.value = state.copy(
                    isLoading = false,
                    error = result.exceptionOrNull()?.message
                )

                launch {
                    delay(2000)
                    _uiState.value = _uiState.value.copy(error = null)
                }
            }
        }
    }

    fun onDeleteClick() {
        _uiState.value = _uiState.value.copy(showDeleteDialog = true)
    }

    fun onDismissDeleteDialog() {
        _uiState.value = _uiState.value.copy(showDeleteDialog = false)
    }

    fun confirmDeleteAccount(onAccountDeleted: () -> Unit) {
        viewModelScope.launch {
            val result = userRepository.deleteAccount()
            if (result.isSuccess) {
                onAccountDeleted()
            } else {
                _uiState.value = _uiState.value.copy(
                    showDeleteDialog = false,
                    error = result.exceptionOrNull()?.message
                )
            }
        }
    }

    fun cancelEdit() {
        viewModelScope.launch {
            val user = userRepository.getCurrentUser()
            _uiState.value = if (user != null) {
                _uiState.value.copy(
                    avatar = user.avatar,
                    name = user.name,
                    password = user.password,
                    isEditing = false,
                    showSuccessMessage = false,
                    error = null
                )
            } else {
                _uiState.value.copy(
                    isEditing = false,
                    showSuccessMessage = false,
                    error = null
                )
            }
        }
    }
}