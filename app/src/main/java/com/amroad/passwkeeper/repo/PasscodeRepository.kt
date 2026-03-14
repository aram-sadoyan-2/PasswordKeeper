package com.amroad.passwkeeper.repo

import android.util.Base64
import com.amroad.passwkeeper.helper.PasscodeCrypto
import com.amroad.passwkeeper.helper.SecurePrefs

class PasscodeRepository(private val prefs: SecurePrefs) {

    companion object {
        private const val KEY_ENABLED = "pass_enabled"
        private const val KEY_SALT = "pass_salt"
        private const val KEY_HASH = "pass_hash"
        private const val KEY_RECOVERY_SET = "recovery_question_set"

        private const val KEY_RECOVERY_QUESTION = "recovery_question"
        private const val KEY_RECOVERY_ANSWER_SALT = "recovery_answer_salt"
        private const val KEY_RECOVERY_ANSWER_HASH = "recovery_answer_hash"
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

    fun saveRecoveryQuestion(question: String, answer: String) {
        val normalizedAnswer = answer.trim()
        val salt = PasscodeCrypto.generateSalt()
        val hash = PasscodeCrypto.pbkdf2(normalizedAnswer.toCharArray(), salt)

        prefs.putString(KEY_RECOVERY_QUESTION, question)
        prefs.putString(KEY_RECOVERY_ANSWER_SALT, Base64.encodeToString(salt, Base64.NO_WRAP))
        prefs.putString(KEY_RECOVERY_ANSWER_HASH, Base64.encodeToString(hash, Base64.NO_WRAP))
        prefs.putBoolean(KEY_RECOVERY_SET, true)
    }

    fun getRecoveryQuestion(): String? = prefs.getString(KEY_RECOVERY_QUESTION)

    fun verifyRecoveryAnswer(answer: String): Boolean {
        val saltB64 = prefs.getString(KEY_RECOVERY_ANSWER_SALT) ?: return false
        val hashB64 = prefs.getString(KEY_RECOVERY_ANSWER_HASH) ?: return false

        val salt = Base64.decode(saltB64, Base64.NO_WRAP)
        val storedHash = Base64.decode(hashB64, Base64.NO_WRAP)
        val inputHash = PasscodeCrypto.pbkdf2(answer.trim().toCharArray(), salt)

        return PasscodeCrypto.constantTimeEquals(storedHash, inputHash)
    }
}