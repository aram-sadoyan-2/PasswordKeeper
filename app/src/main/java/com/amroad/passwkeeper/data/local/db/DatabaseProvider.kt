package com.amroad.passwkeeper.data.local.db

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "pass_keeper_db"
        ).build()
    }
}