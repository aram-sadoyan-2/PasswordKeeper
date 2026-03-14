package com.amroad.passwkeeper.di

import androidx.room.Room
import com.amroad.passwkeeper.data.local.db.AppDatabase
import com.amroad.passwkeeper.data.repository.GeneratedPasswordRepository
import com.amroad.passwkeeper.ui.screen.generator.GeneratorViewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "pass_keeper_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().generatedPasswordDao() }
    single { GeneratedPasswordRepository(get()) }

    factory { GeneratorViewModel(get()) }
}
