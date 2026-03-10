package com.amroad.passwkeeper.ui.tab.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.amroad.passwkeeper.data.VaultFolder
import com.amroad.passwkeeper.ui.component.SearchWithEditBarIosStyle
import com.amroad.passwkeeper.ui.component.VaultFolderSwipeItem

@Composable
fun VaultScreen() {
    val folders = remember {
        mutableStateListOf(
            VaultFolder(1, "Password Vault", true),
            VaultFolder(2, "Work Accounts", false),
            VaultFolder(3, "Social Media", false),
            VaultFolder(4, "Crypto Wallets", false),
            VaultFolder(5, "Crypto Wallets", false),
            VaultFolder(6, "Crypto Wallets", false),
            VaultFolder(7, "Crypto Wallets", false),
            VaultFolder(8, "Crypto Wallets", false),
        )
    }

    val pinnedFolders = folders.filter { it.pinned }
    val unpinnedFolders = folders.filterNot { it.pinned }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE2E2E2))
            .padding(top = 64.dp)
    ) {
        SearchScreen(
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
                    title = folder.title,
                    subtitle = "Folder",
                    isPinned = folder.pinned,
                    isSelected = false,
                    onClick = {},
                    onSelect = {},
                    onPinClick = {
                        val index = folders.indexOfFirst { it.id == folder.id }
                        if (index != -1) {
                            folders[index] = folder.copy(pinned = !folder.pinned)
                        }
                    },
                    onRename = {},
                    onCopy = {},
                    onDelete = {
                        folders.remove(folder)
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
                    title = folder.title,
                    subtitle = "Folder",
                    isPinned = folder.pinned,
                    isSelected = false,
                    onClick = {},
                    onSelect = {},
                    onPinClick = {
                        val index = folders.indexOfFirst { it.id == folder.id }
                        if (index != -1) {
                            folders[index] = folder.copy(pinned = !folder.pinned)
                        }
                    },
                    onRename = {},
                    onCopy = {},
                    onDelete = {
                        folders.remove(folder)
                    }
                )
            }
        }
    }
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier
) {
    var search by remember { mutableStateOf("") }

    SearchWithEditBarIosStyle(
        value = search,
        onValueChange = { search = it },
        onEditClick = {},
        modifier = modifier
    )
}