package com.amroad.passwkeeper.ui.tab

import androidx.compose.ui.unit.dp

enum class HomeTab { VAULT, GENERATOR, SETTINGS }

data class TabItem(
    val tab: HomeTab,
    val title: String,
    val iconRes: Int,
    val iconSize: androidx.compose.ui.unit.Dp = 22.dp
)