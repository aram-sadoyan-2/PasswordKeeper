package com.amroad.passwkeeper.ui.passcodetype

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
fun PasscodeConfirmScreen(
    vm: PasscodeViewModel,
    onBack: () -> Unit,
    onConfirmed: () -> Unit
) {
    LaunchedEffect(vm.input) {
        if (vm.input.length == 6) onConfirmed()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFE2E2E2))
            .padding(horizontal = 24.dp)
    ) {
        Spacer(Modifier.height(24.dp))
        Text("←", modifier = Modifier.clickable { onBack() }, color = Color(0xFF2D2D2D))

        Spacer(Modifier.height(60.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Text("Enter Passcode Again to Confirm", color = Color(0xFF1C1C1C))
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
                onDigit = { vm.clearError(); vm.onDigit(it) },
                onBackspace = { vm.clearError(); vm.onBackspace() },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}