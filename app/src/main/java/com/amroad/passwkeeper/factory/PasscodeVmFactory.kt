package com.amroad.passwkeeper.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amroad.passwkeeper.helper.SecurePrefs
import com.amroad.passwkeeper.repo.PasscodeRepository
import com.amroad.passwkeeper.viewmodel.PasscodeViewModel

class PasscodeVmFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val prefs = SecurePrefs(context.applicationContext)
        val repo = PasscodeRepository(prefs)
        @Suppress("UNCHECKED_CAST")
        return PasscodeViewModel(repo) as T
    }
}