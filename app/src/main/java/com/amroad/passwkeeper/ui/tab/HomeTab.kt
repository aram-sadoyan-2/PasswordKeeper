package com.amroad.passwkeeper.ui.tab

enum class HomeTab { VAULT, GENERATOR, SETTINGS }

data class TabItem(
    val tab: HomeTab,
    val title: String,
    val iconRes: Int,
)