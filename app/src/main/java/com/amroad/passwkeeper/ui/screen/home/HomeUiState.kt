package com.amroad.passwkeeper.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amroad.passwkeeper.data.local.entity.FolderEntity
import com.amroad.passwkeeper.data.repository.VaultRepository
import com.amroad.passwkeeper.repo.PasscodeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

data class HomeUiState(
    val folders: List<FolderEntity> = emptyList(),
    val isLoading: Boolean = true,
    val requirePassOnLaunch: Boolean = false
)

class HomeViewModel(
    private val repository: VaultRepository,
    private val passcodeRepository: PasscodeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(
        requirePassOnLaunch = passcodeRepository.isRequirePassOnLaunch()
    ))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        observeFolders()
    }

    private fun observeFolders() {
        repository.getFolders()
            .onEach { folders ->
                _uiState.value = _uiState.value.copy(
                    folders = folders,
                    isLoading = false
                )
            }
            .launchIn(viewModelScope)
    }

    fun onRequirePassOnLaunchChange(enabled: Boolean) {
        passcodeRepository.setRequirePassOnLaunch(enabled)
        _uiState.value = _uiState.value.copy(requirePassOnLaunch = enabled)
    }

    fun createFolder() {
        viewModelScope.launch {
            repository.createFolder()
        }
    }

    fun renameFolder(folderId: Long, title: String, subtitle: String) {
        viewModelScope.launch {
            repository.renameFolder(
                folderId = folderId,
                newName = title,
                newSubtitle = subtitle
            )
        }
    }

    fun deleteFolder(folder: FolderEntity) {
        viewModelScope.launch {
            repository.deleteFolder(folder)
        }
    }

    fun togglePin(folder: FolderEntity) {
        viewModelScope.launch {
            repository.updateFolder(
                folder.copy(
                    isPinned = !folder.isPinned
                )
            )
        }
    }
}