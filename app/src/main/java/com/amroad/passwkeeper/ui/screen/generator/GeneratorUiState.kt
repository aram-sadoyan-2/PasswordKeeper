package com.amroad.passwkeeper.ui.screen.generator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amroad.passwkeeper.data.local.entity.GeneratedPasswordEntity
import com.amroad.passwkeeper.data.repository.GeneratedPasswordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

data class GeneratorUiState(
    val passwordLength: Float = 20f,
    val lowercaseEnabled: Boolean = true,
    val uppercaseEnabled: Boolean = true,
    val numbersEnabled: Boolean = true,
    val specialEnabled: Boolean = true,
    val similarEnabled: Boolean = true,
    val generatedPasswords: List<GeneratedPasswordUi> = emptyList()
)

class GeneratorViewModel(
    private val repository: GeneratedPasswordRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GeneratorUiState())
    val uiState: StateFlow<GeneratorUiState> = _uiState.asStateFlow()

    init {
        observeGeneratedPasswords()
    }

    private fun observeGeneratedPasswords() {
        repository.getGeneratedPasswords()
            .onEach { list ->
                _uiState.value = _uiState.value.copy(
                    generatedPasswords = list.map { it.toUi() }
                )
            }
            .launchIn(viewModelScope)
    }

    fun onPasswordLengthChange(value: Float) {
        _uiState.value = _uiState.value.copy(passwordLength = value)
    }

    fun onLowercaseChange(value: Boolean) {
        _uiState.value = _uiState.value.copy(lowercaseEnabled = value)
    }

    fun onUppercaseChange(value: Boolean) {
        _uiState.value = _uiState.value.copy(uppercaseEnabled = value)
    }

    fun onNumbersChange(value: Boolean) {
        _uiState.value = _uiState.value.copy(numbersEnabled = value)
    }

    fun onSpecialChange(value: Boolean) {
        _uiState.value = _uiState.value.copy(specialEnabled = value)
    }

    fun onSimilarChange(value: Boolean) {
        _uiState.value = _uiState.value.copy(similarEnabled = value)
    }

    fun generatePassword() {
        val state = _uiState.value

        val password = generatePassword(
            length = state.passwordLength.toInt(),
            useLowercase = state.lowercaseEnabled,
            useUppercase = state.uppercaseEnabled,
            useNumbers = state.numbersEnabled,
            useSpecial = state.specialEnabled,
            includeSimilar = state.similarEnabled
        )

        if (password.isEmpty()) return

        val strength = calculatePasswordStrength(
            password = password,
            useLowercase = state.lowercaseEnabled,
            useUppercase = state.uppercaseEnabled,
            useNumbers = state.numbersEnabled,
            useSpecial = state.specialEnabled
        )

        viewModelScope.launch {
            repository.insertGeneratedPassword(
                GeneratedPasswordEntity(
                    title = "Password_${state.generatedPasswords.size + 1}",
                    password = password,
                    strength = strength.name
                )
            )
        }
    }

    fun deletePassword(id: Long) {
        viewModelScope.launch {
            repository.deleteGeneratedPassword(id)
        }
    }
}
