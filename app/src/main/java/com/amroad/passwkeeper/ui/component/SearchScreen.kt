package com.amroad.passwkeeper.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.amroad.passwkeeper.ui.component.SearchWithEditBarIosStyle

@Composable
fun SearchScreen(
    value: String,
    onValueChange: (String) -> Unit,
    isEditMode: Boolean,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SearchWithEditBarIosStyle(
        value = value,
        onValueChange = onValueChange,
        editText = if (isEditMode) "Done" else "Edit",
        onEditClick = onEditClick,
        modifier = modifier
    )
}