package com.amroad.passwkeeper.ui.tab.tabscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amroad.passwkeeper.R
import com.amroad.passwkeeper.ui.component.AppSearchField
import com.amroad.passwkeeper.ui.component.FolderItemRow
import com.amroad.passwkeeper.ui.component.FolderPreviewTopBar
import com.amroad.passwkeeper.ui.screen.folderdetails.FolderDetailsViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun FolderPreviewScreen(
    folderId: Long,
    onBackClick: () -> Unit
) {
    val viewModel: FolderDetailsViewModel = koinViewModel(
        key = "folder_details_$folderId",
        parameters = { parametersOf(folderId) }
    )

    val uiState by viewModel.uiState.collectAsState()

    var search by remember { mutableStateOf("") }
    var isHidden by remember { mutableStateOf(false) }

    val folderTitle = uiState.folder?.folder?.name.orEmpty()
    val items = uiState.items

    var isEditMode by remember { mutableStateOf(false) }
    var editModeChangeKey by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE2E2E2))
            .padding(top = 66.dp)
    ) {
        FolderPreviewTopBar(
            title = folderTitle,
            subtitle = "Folder",
            onBackClick = onBackClick
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp)
                .padding(top = 30.dp, bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppSearchField(
                value = search,
                onValueChange = {
                    search = it
                    viewModel.onSearchQueryChanged(it)
                },
                placeholder = "Search",
                modifier = Modifier.weight(1f)
            )

            Image(
                painter = painterResource(
                    id = if (isHidden) R.drawable.ic_password_hidden else R.drawable.ic_password_shown
                ),
                contentDescription = if (isHidden) "Show items" else "Hide items",
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        isHidden = !isHidden
                    }
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        color = Color(0xFF1D42D9),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .defaultMinSize(minHeight = 30.dp)
                    .clickable {
                        isEditMode = !isEditMode
                        editModeChangeKey++
                    }
                    .padding(horizontal = 18.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isEditMode) "Done" else "Edit",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.heebo_regular)),
                        fontWeight = FontWeight.W600,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    )
                )
            }

        }



        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = items,
                key = { it.id }
            ) { item ->
                FolderItemRow(
                    item = item,
                    isHidden = isHidden,
                    isGlobalEditMode = isEditMode,
                    globalEditChangeKey = editModeChangeKey,
                    onDeleteClick = {
                        viewModel.deleteItem(item)
                    },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 0.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_add_notegroup),
                contentDescription = "Add item",
                modifier = Modifier
                    .size(58.dp)
                    .clickable {
                        viewModel.createItem(
                            title = "Title2",
                            notePrimaryName = "Notes2",
                            notePrimaryValue = "",
                            noteSecondaryName = "Notes2",
                            noteSecondaryValue = "",
                            noteAdditional = "Notes2"
                        )
                    }
            )
        }
    }
}