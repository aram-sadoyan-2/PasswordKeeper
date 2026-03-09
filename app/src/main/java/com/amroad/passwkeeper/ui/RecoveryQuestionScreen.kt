package com.amroad.passwkeeper.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign

@Composable
fun RecoveryQuestionScreen(
    onLater: () -> Unit,
    onDone: () -> Unit
) {
    Column(Modifier
        .fillMaxSize()
        .padding(24.dp)) {
        Spacer(Modifier.height(30.dp))
        Text(
            "Security & Password Recovery",
            color = Color(0xFF1D42D9),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(12.dp))
        Text(
            "For your security, the app does not store your password anywhere. " +
                    "If you forget it, access to the app cannot be restored.\n\n" +
                    "We recommend setting a security question to help you regain access if needed."
        )

        Spacer(Modifier.height(22.dp))
        Text("CHOOSE A SECURITY QUESTION", style = MaterialTheme.typography.labelMedium)

        Spacer(Modifier.height(12.dp))
        listOf(
            "What was the name of your first school?",
            "What city were you born in?",
            "What was the name of your childhood pet?",
            "What is the name of the street you grew up on?",
            "What is your favorite book or movie?"
        ).forEach {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { /* open answer screen */ }
            ) {
                Row(Modifier
                    .fillMaxWidth()
                    .padding(16.dp)) {
                    Text(it, modifier = Modifier.weight(1f))
                    Text("›")
                }
            }
        }

        Spacer(Modifier.weight(1f))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            TextButton(onClick = onLater) { Text("Later") }
            Button(onClick = onDone) { Text("Done") }
        }
    }
}