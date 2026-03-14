package com.amroad.passwkeeper.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "generated_passwords")
data class GeneratedPasswordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val password: String,
    val strength: String,
    val createdAt: Long = System.currentTimeMillis()
)
