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
fun PasscodeSetupScreen(
    vm: PasscodeViewModel,
    onCancel: () -> Unit,
    onNext: () -> Unit
) {
    LaunchedEffect(vm.input) {
        if (vm.input.length == 6) onNext()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFE2E2E2))
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(90.dp))
        Text("Enter Passcode", color = Color(0xFF1C1C1C))
        Spacer(Modifier.height(18.dp))

        PasscodeDots(
            filled = vm.input.length,
            total = 6
        )

        Spacer(Modifier.height(50.dp))

        PasscodeKeypad(
            onDigit = { vm.clearError(); vm.onDigit(it) },
            onBackspace = { vm.clearError(); vm.onBackspace() },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.weight(1f))

        Button(
            onClick = onCancel,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF363636),
                contentColor = Color.White
            )
        ) {
            Text("Cancel Passcode Setup")
        }

        Spacer(Modifier.height(10.dp))
        Text(
            "Password can be set anytime in Settings.",
            color = Color(0xFF7A7A7A),
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(Modifier.height(36.dp))
    }
}