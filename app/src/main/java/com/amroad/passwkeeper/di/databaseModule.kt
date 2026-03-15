package com.amroad.passwkeeper.di


import androidx.room.Room
import com.amroad.passwkeeper.data.local.db.AppDatabase
import com.amroad.passwkeeper.data.repository.VaultRepository
import com.amroad.passwkeeper.helper.SecurePrefs
import com.amroad.passwkeeper.repo.PasscodeRepository
import com.amroad.passwkeeper.ui.screen.home.HomeViewModel
import com.amroad.passwkeeper.ui.screen.folderdetails.FolderDetailsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {

    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "pass_keeper_db"
        ).build()
    }

    single { get<AppDatabase>().vaultDao() }

    single { VaultRepository(get()) }

    single { SecurePrefs(androidContext()) }

    single { PasscodeRepository(get()) }

    viewModel { HomeViewModel(get(), get()) }

    viewModel { (folderId: Long) ->
        FolderDetailsViewModel(
            repository = get(),
            folderId = folderId
        )
    }
}