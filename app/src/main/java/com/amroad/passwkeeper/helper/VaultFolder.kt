package com.amroad.passwkeeper.helper

data class VaultFolder(
    val id: Int,
    val title: String,
    val pinned: Boolean = false
)