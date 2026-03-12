package com.amroad.passwkeeper.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.amroad.passwkeeper.data.local.entity.FolderEntity
import com.amroad.passwkeeper.data.local.entity.VaultItemEntity
import com.amroad.passwkeeper.data.local.relation.FolderWithItems
import kotlinx.coroutines.flow.Flow

@Dao
interface VaultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFolder(folder: FolderEntity): Long

    @Update
    suspend fun updateFolder(folder: FolderEntity)

    @Delete
    suspend fun deleteFolder(folder: FolderEntity)

    @Query("SELECT * FROM folders ORDER BY updatedAt DESC")
    fun getFolders(): Flow<List<FolderEntity>>

    @Query("SELECT * FROM folders WHERE id = :folderId LIMIT 1")
    suspend fun getFolderById(folderId: Long): FolderEntity?

    @Transaction
    @Query("SELECT * FROM folders ORDER BY updatedAt DESC")
    fun getFoldersWithItems(): Flow<List<FolderWithItems>>

    @Transaction
    @Query("SELECT * FROM folders WHERE id = :folderId LIMIT 1")
    fun getFolderWithItems(folderId: Long): Flow<FolderWithItems?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: VaultItemEntity): Long

    @Update
    suspend fun updateItem(item: VaultItemEntity)

    @Delete
    suspend fun deleteItem(item: VaultItemEntity)

    @Query("DELETE FROM vault_items WHERE id = :itemId")
    suspend fun deleteItemById(itemId: Long)

    @Query("SELECT * FROM vault_items WHERE id = :itemId LIMIT 1")
    suspend fun getItemById(itemId: Long): VaultItemEntity?

    @Query("""
        SELECT * FROM vault_items
        WHERE folderId = :folderId
        ORDER BY updatedAt DESC
    """)
    fun getItemsByFolder(folderId: Long): Flow<List<VaultItemEntity>>

    @Query("""
        SELECT * FROM vault_items
        WHERE folderId = :folderId
          AND (
            title LIKE '%' || :query || '%'
            OR notePrimaryName LIKE '%' || :query || '%'
            OR notePrimaryValue LIKE '%' || :query || '%'
            OR noteSecondaryName LIKE '%' || :query || '%'
            OR noteSecondaryValue LIKE '%' || :query || '%'
            OR noteAdditional LIKE '%' || :query || '%'
          )
        ORDER BY updatedAt DESC
    """)
    fun searchItems(folderId: Long, query: String): Flow<List<VaultItemEntity>>
}