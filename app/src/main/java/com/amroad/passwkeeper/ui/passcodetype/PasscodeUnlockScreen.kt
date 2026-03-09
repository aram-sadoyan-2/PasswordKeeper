package com.amroad.passwkeeper.ui.passcodetype

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.amroad.passwkeeper.ui.PasscodeDots
import com.amroad.passwkeeper.ui.PasscodeKeypad
import com.amroad.passwkeeper.viewmodel.PasscodeViewModel

@Composable
fun PasscodeUnlockScreen(
    vm: PasscodeViewModel,
    onUnlocked: () -> Unit
) {

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
        Text("Enter Passcode unlock", color = Color(0xFF1C1C1C))
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


    }
}