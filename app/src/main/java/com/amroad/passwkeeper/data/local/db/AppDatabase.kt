package com.amroad.passwkeeper.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amroad.passwkeeper.data.local.dao.GeneratedPasswordDao
import com.amroad.passwkeeper.data.local.dao.VaultDao
import com.amroad.passwkeeper.data.local.entity.FolderEntity
import com.amroad.passwkeeper.data.local.entity.GeneratedPasswordEntity
import com.amroad.passwkeeper.data.local.entity.VaultItemEntity

@Database(
    entities = [
        FolderEntity::class,
        VaultItemEntity::class,
        GeneratedPasswordEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vaultDao(): VaultDao
    abstract fun generatedPasswordDao(): GeneratedPasswordDao
}
