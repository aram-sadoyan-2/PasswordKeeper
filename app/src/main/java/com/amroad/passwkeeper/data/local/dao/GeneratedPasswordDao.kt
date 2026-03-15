package com.amroad.passwkeeper.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amroad.passwkeeper.data.local.entity.GeneratedPasswordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GeneratedPasswordDao {

    @Query("SELECT * FROM generated_passwords ORDER BY createdAt DESC")
    fun getGeneratedPasswords(): Flow<List<GeneratedPasswordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGeneratedPassword(item: GeneratedPasswordEntity)

    @Query("DELETE FROM generated_passwords WHERE id = :id")
    suspend fun deleteGeneratedPassword(id: Long)

    @Query("DELETE FROM generated_passwords")
    suspend fun clearGeneratedPasswords()

    @Query("""
    UPDATE generated_passwords
    SET title = :title,
        password = :password,
        strength = :strength
    WHERE id = :id
""")
    suspend fun updateGeneratedPassword(
        id: Long,
        title: String,
        password: String,
        strength: String
    )
}
