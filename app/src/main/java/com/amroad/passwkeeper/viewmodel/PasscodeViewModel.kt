package com.amroad.passwkeeper.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.amroad.passwkeeper.repo.PasscodeRepository

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PasscodeViewModel(
    private val repo: PasscodeRepository
) : ViewModel() {

    var input by mutableStateOf("")
        private set

    var draftPasscode: String? by mutableStateOf(null)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun clearError() { error = null }

    fun onDigit(d: Int) {
        if (input.length >= 6) return
        input += d.toString()
    }

    fun onBackspace() {
        if (input.isNotEmpty()) input = input.dropLast(1)
    }

    fun resetInput() { input = "" }

    fun cancelSetup() {
        // user skipped
        draftPasscode = null
        input = ""
        error = null
    }

    fun onFirstEntryComplete(): Boolean {
        if (input.length != 6) return false
        draftPasscode = input
        input = ""
        return true
    }

    var isVerifying by mutableStateOf(false)
        private set

    fun unlockAsync(onResult: (Boolean) -> Unit) {
        if (isVerifying) return
        if (input.length != 6) return

        val pass = input // snapshot
        isVerifying = true
        error = null

        viewModelScope.launch {
            val ok = withContext(Dispatchers.Default) {
                repo.verifyPasscode(pass)   // PBKDF2 here, not UI thread
            }

            if (!ok) {
                input = ""
                error = "Wrong passcode"
            } else {
                input = ""
                error = null
            }

            isVerifying = false
            onResult(ok)
        }
    }
    fun onConfirmComplete(): ConfirmResult {
        if (input.length != 6) return ConfirmResult.Incomplete
        val first = draftPasscode
        if (first == null) return ConfirmResult.Error("No draft passcode")
        if (first != input) {
            input = ""
            error = "Passcodes didn’t match"
            return ConfirmResult.Mismatch
        }
        // save
        repo.savePasscode(input)
        input = ""
        draftPasscode = null
        error = null
        return ConfirmResult.Success
    }

    fun onUnlockComplete(): Boolean {
        if (input.length != 6) return false
        val ok = repo.verifyPasscode(input)
        if (!ok) {
            input = ""
            error = "Wrong passcode"
        } else {
            input = ""
            error = null
        }
        return ok
    }

    fun isPasscodeEnabled(): Boolean = repo.isEnabled()

    fun isRecoveryQuestionSet(): Boolean = repo.isRecoveryQuestionSet()
    fun markRecoveryQuestionSet() = repo.setRecoveryQuestionSet(true)
}

sealed class ConfirmResult {
    data object Incomplete : ConfirmResult()
    data object Success : ConfirmResult()
    data object Mismatch : ConfirmResult()
    data class Error(val msg: String) : ConfirmResult()
}