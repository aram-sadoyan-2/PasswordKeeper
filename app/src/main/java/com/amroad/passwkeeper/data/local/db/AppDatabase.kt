package com.amroad.passwkeeper.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amroad.passwkeeper.data.local.dao.VaultDao
import com.amroad.passwkeeper.data.local.entity.FolderEntity
import com.amroad.passwkeeper.data.local.entity.VaultItemEntity

@Database(
    entities = [
        FolderEntity::class,
        VaultItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vaultDao(): VaultDao
}