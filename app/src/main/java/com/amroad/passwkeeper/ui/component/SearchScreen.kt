package com.amroad.passwkeeper.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.amroad.passwkeeper.ui.component.SearchWithEditBarIosStyle

@Composable
fun SearchScreen(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    SearchWithEditBarIosStyle(
        value = value,
        onValueChange = onValueChange,
        onEditClick = {},
        modifier = modifier
    )
}