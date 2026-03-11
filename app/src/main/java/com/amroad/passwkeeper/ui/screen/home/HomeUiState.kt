package com.amroad.passwkeeper.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amroad.passwkeeper.data.local.entity.FolderEntity
import com.amroad.passwkeeper.data.repository.VaultRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

data class HomeUiState(
    val folders: List<FolderEntity> = emptyList(),
    val isLoading: Boolean = true
)

class HomeViewModel(
    private val repository: VaultRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        observeFolders()
    }

    private fun observeFolders() {
        repository.getFolders()
            .onEach { folders ->
                _uiState.value = HomeUiState(
                    folders = folders,
                    isLoading = false
                )
            }
            .launchIn(viewModelScope)
    }

    fun createFolder(name: String) {
        viewModelScope.launch {
            repository.createFolder(name)
        }
    }

    fun renameFolder(folderId: Long, newName: String) {
        viewModelScope.launch {
            repository.renameFolder(folderId, newName)
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