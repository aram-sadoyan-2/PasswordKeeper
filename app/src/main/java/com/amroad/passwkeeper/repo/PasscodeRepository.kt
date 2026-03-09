package com.amroad.passwkeeper.repo

import android.util.Base64
import com.amroad.passwkeeper.data.PasscodeCrypto
import com.amroad.passwkeeper.data.SecurePrefs

class PasscodeRepository(private val prefs: SecurePrefs) {

    companion object {
        private const val KEY_ENABLED = "pass_enabled"
        private const val KEY_SALT = "pass_salt"
        private const val KEY_HASH = "pass_hash"
        private const val KEY_RECOVERY_SET = "recovery_question_set"
    }

    fun isEnabled(): Boolean = prefs.getBoolean(KEY_ENABLED, false)

    fun disable() {
        prefs.putBoolean(KEY_ENABLED, false)
        prefs.clear(KEY_SALT)
        prefs.clear(KEY_HASH)
    }

    fun savePasscode(passcode: String) {
        val salt = PasscodeCrypto.generateSalt()
        val hash = PasscodeCrypto.pbkdf2(passcode.toCharArray(), salt)

        prefs.putString(KEY_SALT, Base64.encodeToString(salt, Base64.NO_WRAP))
        prefs.putString(KEY_HASH, Base64.encodeToString(hash, Base64.NO_WRAP))
        prefs.putBoolean(KEY_ENABLED, true)
    }

    fun verifyPasscode(passcode: String): Boolean {
        val saltB64 = prefs.getString(KEY_SALT) ?: return false
        val hashB64 = prefs.getString(KEY_HASH) ?: return false

        val salt = Base64.decode(saltB64, Base64.NO_WRAP)
        val storedHash = Base64.decode(hashB64, Base64.NO_WRAP)

        val inputHash = PasscodeCrypto.pbkdf2(passcode.toCharArray(), salt)
        return PasscodeCrypto.constantTimeEquals(storedHash, inputHash)
    }

    fun isRecoveryQuestionSet(): Boolean = prefs.getBoolean(KEY_RECOVERY_SET, false)
    fun setRecoveryQuestionSet(value: Boolean) = prefs.putBoolean(KEY_RECOVERY_SET, value)
}