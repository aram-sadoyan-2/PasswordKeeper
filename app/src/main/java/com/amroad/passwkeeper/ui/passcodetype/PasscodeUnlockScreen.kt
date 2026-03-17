package com.amroad.passwkeeper.ui.passcodetype

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.amroad.passwkeeper.ui.PasscodeDots
import com.amroad.passwkeeper.ui.PasscodeKeypad
import com.amroad.passwkeeper.ui.component.RecoveryAnswerPopup
import com.amroad.passwkeeper.viewmodel.PasscodeViewModel

@Composable
fun PasscodeUnlockScreen(
    vm: PasscodeViewModel,
    onUnlocked: () -> Unit,
    onResetPasscode: () -> Unit
) {
    var showRecoveryPopup by remember { mutableStateOf(false) }
    var recoveryAnswer by remember { mutableStateOf("") }
    var recoveryError by remember { mutableStateOf<String?>(null) }

    val savedQuestion = vm.getRecoveryQuestion()

    LaunchedEffect(Unit) {
        vm.resetInput()
        vm.clearError()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFE2E2E2))
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(90.dp))

        Text(
            text = "Enter Passcode to unlock",
            color = Color(0xFF1C1C1C)
        )

        Spacer(Modifier.height(18.dp))

        PasscodeDots(
            filled = vm.input.length,
            total = 6
        )

        vm.error?.let {
            Spacer(Modifier.height(12.dp))
            Text(it, color = Color(0xFFD32F2F))
        }

        Spacer(Modifier.height(50.dp))

        PasscodeKeypad(
            onDigit = { digit ->
                vm.clearError()
                vm.onDigit(digit)

                if (vm.input.length == 6) {
                    onUnlocked()
                }
            },
            onBackspace = {
                vm.clearError()
                vm.onBackspace()
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        if (vm.isRecoveryQuestionSet() && !savedQuestion.isNullOrBlank()) {
            TextButton(
                onClick = {
                    recoveryAnswer = ""
                    recoveryError = null
                    showRecoveryPopup = true
                }
            ) {
                Text(
                    text = "Forgot password?",
                    color = Color(0xFF1D42D9)
                )
            }
        }
    }

    if (showRecoveryPopup && !savedQuestion.isNullOrBlank()) {
        RecoveryAnswerPopup(
            title = "Password Recovery",
            question = savedQuestion,
            answer = recoveryAnswer,
            answerError = recoveryError,
            onAnswerChange = {
                recoveryAnswer = it
                recoveryError = null
            },
            onDismiss = {
                showRecoveryPopup = false
                recoveryAnswer = ""
                recoveryError = null
            },
            onSave = {
                val trimmed = recoveryAnswer.trim()

                if (trimmed.isBlank()) {
                    recoveryError = "Answer cannot be empty"
                    return@RecoveryAnswerPopup
                }

                val isCorrect = vm.verifyRecoveryAnswer(trimmed)

                if (isCorrect) {
                    showRecoveryPopup = false
                    recoveryAnswer = ""
                    recoveryError = null
                    onResetPasscode()
                } else {
                    recoveryError = "Try Again"
                }
            }
        )
    }
}