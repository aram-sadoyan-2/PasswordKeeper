package com.amroad.passwkeeper.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folders")
data class FolderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val subtitle: String = "",
    val isPinned: Boolean = false,
    val createdAt: Long,
    val updatedAt: Long
)