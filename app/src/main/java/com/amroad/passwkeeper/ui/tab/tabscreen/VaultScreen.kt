package com.amroad.passwkeeper.ui.tab.tabscreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.amroad.passwkeeper.R
import com.amroad.passwkeeper.ui.component.SearchScreen
import com.amroad.passwkeeper.ui.component.VaultFolderSwipeItem
import com.amroad.passwkeeper.ui.screen.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun VaultScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val folders = uiState.folders

    var openedFolderId by remember { mutableStateOf<Long?>(null) }
    var search by remember { mutableStateOf("") }

    BackHandler(enabled = openedFolderId != null) {
        openedFolderId = null
    }

    val currentFolderId = openedFolderId
    if (currentFolderId != null) {
        key(currentFolderId) {
            FolderPreviewScreen(
                folderId = currentFolderId,
                onBackClick = { openedFolderId = null }
            )
        }
        return
    }

    val filteredFolders = if (search.isBlank()) {
        folders
    } else {
        folders.filter { it.name.contains(search, ignoreCase = true) }
    }

    val pinnedFolders = filteredFolders.filter { it.isPinned }
    val unpinnedFolders = filteredFolders.filterNot { it.isPinned }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE2E2E2))
            .padding(top = 64.dp)
    ) {
        SearchScreen(
            value = search,
            onValueChange = { search = it },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = pinnedFolders,
                key = { it.id }
            ) { folder ->
                VaultFolderSwipeItem(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    title = folder.name,
                    subtitle = "Folder",
                    isPinned = folder.isPinned,
                    isSelected = false,
                    onClick = {
                        openedFolderId = folder.id
                    },
                    onSelect = {},
                    onPinClick = {
                        viewModel.togglePin(folder)
                    },
                    onRename = {
                        viewModel.renameFolder(folder.id, "${folder.name} Renamed")
                    },
                    onCopy = {
                        viewModel.createFolder("${folder.name} Copy")
                    },
                    onDelete = {
                        viewModel.deleteFolder(folder)
                    }
                )
            }

            if (pinnedFolders.isNotEmpty() && unpinnedFolders.isNotEmpty()) {
                item {
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        thickness = 1.dp,
                        color = Color(0xFFBDBDBD)
                    )
                }
            }

            items(
                items = unpinnedFolders,
                key = { it.id }
            ) { folder ->
                VaultFolderSwipeItem(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    title = folder.name,
                    subtitle = "Folder",
                    isPinned = folder.isPinned,
                    isSelected = false,
                    onClick = {
                        openedFolderId = folder.id
                    },
                    onSelect = {},
                    onPinClick = {
                        viewModel.togglePin(folder)
                    },
                    onRename = {
                        viewModel.renameFolder(folder.id, "${folder.name} Renamed")
                    },
                    onCopy = {
                        viewModel.createFolder("${folder.name} Copy")
                    },
                    onDelete = {
                        viewModel.deleteFolder(folder)
                    }
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_add_folder),
                        contentDescription = "Add new folder",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                viewModel.createFolder("New Folder")
                            }
                    )
                }
            }
        }
    }
}