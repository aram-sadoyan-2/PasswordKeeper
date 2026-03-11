package com.amroad.passwkeeper

import android.app.Application
import com.amroad.passwkeeper.di.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PasswKeeperApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PasswKeeperApp)
            modules(
                databaseModule,
//                repositoryModule,
//                viewModelModule
            )
        }
    }
}