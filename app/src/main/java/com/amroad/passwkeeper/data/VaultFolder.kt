package com.amroad.passwkeeper.data

data class VaultFolder(
    val id: Int,
    val title: String,
    val pinned: Boolean = false
)