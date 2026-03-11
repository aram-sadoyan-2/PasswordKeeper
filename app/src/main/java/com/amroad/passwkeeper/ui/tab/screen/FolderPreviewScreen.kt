package com.amroad.passwkeeper.ui.tab.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.amroad.passwkeeper.ui.component.FolderPreviewTopBar

@Composable
fun FolderPreviewScreen(
    folderTitle: String,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE2E2E2))
            .padding(top = 22.dp)
    ) {
        FolderPreviewTopBar(
            title = folderTitle,
            subtitle = "Folder",
            onBackClick = onBackClick
        )

        // next content here
    }
}