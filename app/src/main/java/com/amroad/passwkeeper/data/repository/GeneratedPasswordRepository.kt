package com.amroad.passwkeeper.data.repository

import com.amroad.passwkeeper.data.local.dao.GeneratedPasswordDao
import com.amroad.passwkeeper.data.local.entity.GeneratedPasswordEntity
import kotlinx.coroutines.flow.Flow

class GeneratedPasswordRepository(
    private val dao: GeneratedPasswordDao
) {
    fun getGeneratedPasswords(): Flow<List<GeneratedPasswordEntity>> =
        dao.getGeneratedPasswords()

    suspend fun insertGeneratedPassword(item: GeneratedPasswordEntity) {
        dao.insertGeneratedPassword(item)
    }

    suspend fun deleteGeneratedPassword(id: Long) {
        dao.deleteGeneratedPassword(id)
    }

    suspend fun clearGeneratedPasswords() {
        dao.clearGeneratedPasswords()
    }

    suspend fun updateGeneratedPassword(
        id: Long,
        title: String,
        password: String,
        strength: String
    ) {
        dao.updateGeneratedPassword(
            id = id,
            title = title,
            password = password,
            strength = strength
        )
    }
}
