package com.amroad.passwkeeper.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.amroad.passwkeeper.data.local.entity.FolderEntity
import com.amroad.passwkeeper.data.local.entity.VaultItemEntity

data class FolderWithItems(
    @Embedded
    val folder: FolderEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "folderId"
    )
    val items: List<VaultItemEntity>
)