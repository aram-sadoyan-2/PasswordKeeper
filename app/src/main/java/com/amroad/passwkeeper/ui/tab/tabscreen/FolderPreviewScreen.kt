package com.amroad.passwkeeper.ui.tab.tabscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amroad.passwkeeper.R
import com.amroad.passwkeeper.ui.component.AppSearchField
import com.amroad.passwkeeper.ui.component.FolderPreviewTopBar

@Composable
fun FolderPreviewScreen(
    folderTitle: String,
    onBackClick: () -> Unit
) {
    var search by remember { mutableStateOf("") }
    var isHidden by remember { mutableStateOf(false) }

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
                .padding(horizontal = 26.dp).padding(top = 30.dp, bottom = 50.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppSearchField(
                value = search,
                onValueChange = { search = it },
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

            Text(
                text = "Edit",
                color = Color(0xFF1D42D9),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.heebo_semibold)),
                fontWeight = FontWeight.W600,
                modifier = Modifier.clickable {
                    // edit click
                }
            )
        }
    }
}