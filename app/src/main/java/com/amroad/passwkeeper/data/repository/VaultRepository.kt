package com.amroad.passwkeeper.data.repository

import com.amroad.passwkeeper.data.local.dao.VaultDao
import com.amroad.passwkeeper.data.local.entity.FolderEntity
import com.amroad.passwkeeper.data.local.entity.VaultItemEntity
import com.amroad.passwkeeper.data.local.relation.FolderWithItems
import kotlinx.coroutines.flow.Flow

class VaultRepository(
    private val vaultDao: VaultDao
) {

    fun getFolders(): Flow<List<FolderEntity>> =
        vaultDao.getFolders()

    fun getFoldersWithItems(): Flow<List<FolderWithItems>> =
        vaultDao.getFoldersWithItems()

    fun getFolderWithItems(folderId: Long): Flow<FolderWithItems?> =
        vaultDao.getFolderWithItems(folderId)

    fun getItemsByFolder(folderId: Long): Flow<List<VaultItemEntity>> =
        vaultDao.getItemsByFolder(folderId)

    fun searchItems(folderId: Long, query: String): Flow<List<VaultItemEntity>> =
        vaultDao.searchItems(folderId, query)

    suspend fun createFolder(name: String): Long {
        val now = System.currentTimeMillis()
        return vaultDao.insertFolder(
            FolderEntity(
                name = name,
                createdAt = now,
                updatedAt = now
            )
        )
    }

    suspend fun renameFolder(folderId: Long, newName: String) {
        val oldFolder = vaultDao.getFolderById(folderId) ?: return
        vaultDao.updateFolder(
            oldFolder.copy(
                name = newName,
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun deleteFolder(folder: FolderEntity) {
        vaultDao.deleteFolder(folder)
    }

    suspend fun createItem(
        folderId: Long,
        title: String,
        notePrimaryName: String,
        notePrimaryValue: String,
        noteSecondaryName: String,
        noteSecondaryValue: String,
        noteAdditional: String
    ): Long {
        val now = System.currentTimeMillis()
        return vaultDao.insertItem(
            VaultItemEntity(
                folderId = folderId,
                title = title,
                notePrimaryName = notePrimaryName,
                notePrimaryValue = notePrimaryValue,
                noteSecondaryName = noteSecondaryName,
                noteSecondaryValue = noteSecondaryValue,
                noteAdditional = noteAdditional,
                createdAt = now,
                updatedAt = now
            )
        )
    }

    suspend fun updateItem(
        itemId: Long,
        title: String,
        notePrimaryName: String,
        notePrimaryValue: String,
        noteSecondaryName: String,
        noteSecondaryValue: String,
        noteAdditional: String,
        isPinned: Boolean
    ) {
        val oldItem = vaultDao.getItemById(itemId) ?: return
        vaultDao.updateItem(
            oldItem.copy(
                title = title,
                notePrimaryName = notePrimaryName,
                notePrimaryValue = notePrimaryValue,
                noteSecondaryName = noteSecondaryName,
                noteSecondaryValue = noteSecondaryValue,
                noteAdditional = noteAdditional,
                isPinned = isPinned,
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun updateItem(item: VaultItemEntity) {
        vaultDao.updateItem(
            item.copy(updatedAt = System.currentTimeMillis())
        )
    }

    suspend fun deleteItem(item: VaultItemEntity) {
        vaultDao.deleteItem(item)
    }

    suspend fun deleteItemById(itemId: Long) {
        vaultDao.deleteItemById(itemId)
    }

    suspend fun togglePin(itemId: Long) {
        val oldItem = vaultDao.getItemById(itemId) ?: return
        vaultDao.updateItem(
            oldItem.copy(
                isPinned = !oldItem.isPinned,
                updatedAt = System.currentTimeMillis()
            )
        )
    }
}