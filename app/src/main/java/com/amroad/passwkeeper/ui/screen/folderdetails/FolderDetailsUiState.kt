package com.amroad.passwkeeper.ui.screen.folderdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amroad.passwkeeper.data.local.entity.VaultItemEntity
import com.amroad.passwkeeper.data.local.relation.FolderWithItems
import com.amroad.passwkeeper.data.repository.VaultRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

data class FolderDetailsUiState(
    val folder: FolderWithItems? = null,
    val items: List<VaultItemEntity> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = true
)

class FolderDetailsViewModel(
    private val repository: VaultRepository,
    private val folderId: Long
) : ViewModel() {

    private val _uiState = MutableStateFlow(FolderDetailsUiState())
    val uiState: StateFlow<FolderDetailsUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    init {
        observeFolder()
    }

    private fun observeFolder() {
        repository.getFolderWithItems(folderId)
            .onEach { folderWithItems ->
                _uiState.value = _uiState.value.copy(
                    folder = folderWithItems,
                    items = folderWithItems?.items.orEmpty(),
                    isLoading = false
                )
            }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        searchJob?.cancel()

        if (query.isBlank()) {
            repository.getFolderWithItems(folderId)
                .onEach { folderWithItems ->
                    _uiState.value = _uiState.value.copy(
                        folder = folderWithItems,
                        items = folderWithItems?.items.orEmpty()
                    )
                }
                .launchIn(viewModelScope)
            return
        }

        searchJob = repository.searchItems(folderId, query)
            .onEach { items ->
                _uiState.value = _uiState.value.copy(items = items)
            }
            .launchIn(viewModelScope)
    }

    fun clearSearch() {
        _uiState.value = _uiState.value.copy(searchQuery = "")
        observeFolder()
    }

    fun createItem(
        title: String,
        notePrimaryName: String,
        notePrimaryValue: String,
        noteSecondaryName: String,
        noteSecondaryValue: String,
        noteAdditional: String
    ) {
        viewModelScope.launch {
            repository.createItem(
                folderId = folderId,
                title = title,
                notePrimaryName = notePrimaryName,
                notePrimaryValue = notePrimaryValue,
                noteSecondaryName = noteSecondaryName,
                noteSecondaryValue = noteSecondaryValue,
                noteAdditional = noteAdditional
            )
        }
    }

    fun updateItem(
        itemId: Long,
        title: String,
        notePrimaryName: String,
        notePrimaryValue: String,
        noteSecondaryName: String,
        noteSecondaryValue: String,
        noteAdditional: String,
        isPinned: Boolean
    ) {
        viewModelScope.launch {
            repository.updateItem(
                itemId = itemId,
                title = title,
                notePrimaryName = notePrimaryName,
                notePrimaryValue = notePrimaryValue,
                noteSecondaryName = noteSecondaryName,
                noteSecondaryValue = noteSecondaryValue,
                noteAdditional = noteAdditional,
                isPinned = isPinned
            )
        }
    }

    fun deleteItem(item: VaultItemEntity) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }

    fun togglePin(itemId: Long) {
        viewModelScope.launch {
            repository.toggleItemPin(itemId)
        }
    }
}