package com.amroad.passwkeeper.ui.tab.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
            VaultFolder(4, "Crypto Wallets", false)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE2E2E2))
            .padding(top = 64.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                SearchScreen()
            }

            items(
                items = folders,
                key = { it.id }
            ) { folder ->
                VaultFolderSwipeItem(
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
fun SearchScreen() {
    var search by remember { mutableStateOf("") }

    SearchWithEditBarIosStyle(
        value = search,
        onValueChange = { search = it },
        onEditClick = { },
        modifier = Modifier.padding(bottom = 4.dp)
    )
}